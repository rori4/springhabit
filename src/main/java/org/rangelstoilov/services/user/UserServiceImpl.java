package org.rangelstoilov.services.user;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.rangelstoilov.entities.Role;
import org.rangelstoilov.entities.User;
import org.rangelstoilov.models.view.user.UserAdminViewModel;
import org.rangelstoilov.models.view.user.UserDashboardViewModel;
import org.rangelstoilov.models.view.user.UserRegisterModel;
import org.rangelstoilov.models.view.user.UserRewardModel;
import org.rangelstoilov.repositories.UserRepository;
import org.rangelstoilov.services.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class  UserServiceImpl implements UserService, UserDetailsService {
    private static final int BASE_REWARD_MULTIPLIER = 100;
    public static final int BASE_DMG_MULTIPLIER = 20;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean register(UserRegisterModel userViewModel) {
        User user = this.modelMapper.map(userViewModel, User.class);
        user.setPassword(
                this.passwordEncoder.encode(userViewModel.getPassword())
        );
        this.roleService.addUserAndAdminRoleIfNotExist();
        Role role = this.roleService.findFirstByName("USER");
        role.getUsers().add(user);
        user.getRoles().add(role);

        this.roleService.addRole(role);
        this.userRepository.save(user);
        return true;
    }


    @Override
    public boolean userExists(String email) {
        return this.userRepository.findFirstByEmail(email) != null;
    }


    @Override
    public UserDashboardViewModel getUserDashboardDataByEmail(String email) {
        return modelMapper.map(userRepository.findFirstByEmail(email), UserDashboardViewModel.class);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findFirstByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public UserRewardModel rewardUserForTaskDone(String email, Integer streakMultiplier) {
        UserRewardModel userRewardModel = new UserRewardModel();
        User user = this.userRepository.findFirstByEmail(email);

        //This will determine the exp reward multiplier
        Integer rewardMultiplier = BASE_REWARD_MULTIPLIER * user.getLevel();
        Integer damage = user.getMaxHealth()/BASE_DMG_MULTIPLIER;
        //Possible rewards
        Integer exp = rewardMultiplier * streakMultiplier;
        Integer gold = rewardMultiplier / 2 * streakMultiplier;

        //Setting the user reward model values
        userRewardModel.setExperience(exp);
        userRewardModel.setGold(gold);

        //Handle challengers fight
        List<User> challengesAccepted = user.getChallengesAccepted();
        if(!challengesAccepted.isEmpty()){
            Integer singleChallengerDmg = damage/challengesAccepted.size();
            List<User> deadChallengers = new ArrayList<>();
            for (User challenger : challengesAccepted) {
                if (challenger.getHealth() - singleChallengerDmg <= 0) {
                    int challengerGold = challenger.getGold() / 2;
                    challenger.dead();
                    List<User> challengerChallengesAccepted = challenger.getChallengesAccepted();
                    challengerChallengesAccepted.remove(user);
                    challenger.setChallengesAccepted(challengerChallengesAccepted);
                    deadChallengers.add(challenger);
                    user.setGold(user.getGold()+challengerGold);
                    userRewardModel.setGold(userRewardModel.getGold()+challengerGold);
                    userRewardModel.setKills(userRewardModel.getKills()+1);
                } else {
                    challenger.setHealth(challenger.getHealth()-singleChallengerDmg);
                }
            }
            this.userRepository.saveAll(challengesAccepted);
            challengesAccepted.removeAll(deadChallengers);
            user.setChallengesAccepted(challengesAccepted);
        }


        //When leveling up
        if (user.getExperience() + exp >= user.getNextLevelExp()) {
            userRewardModel.setLevel(1);
            userRewardModel.setHealth(user.getMaxHealth());
            user.levelUp();
            //Setting user entity valus
        }
        user.setExperience(user.getExperience() + exp);
        user.setGold(user.getGold() + gold);

        this.userRepository.save(user);
        return userRewardModel;
    }

    @Override
    public UserRewardModel damageUserForTaskNotDone(String email, Integer streakMultiplier) {
        UserRewardModel userRewardModel = new UserRewardModel();
        User user = this.userRepository.findFirstByEmail(email);
        Integer damage = user.getMaxHealth() / BASE_DMG_MULTIPLIER;
        userRewardModel.setHealth(-damage);
        user.setHealth(user.getHealth() - damage);
        if (user.getHealth() - damage <= 0) {
            userRewardModel.setLevel(-1);
            user.dead();
        }
        this.userRepository.save(user);
        return userRewardModel;
    }

    @Override
    public User findFirstByEmail(String userEmail) {
        return userRepository.findFirstByEmail(userEmail);
    }

    @Override
    public List<UserDashboardViewModel> getAllUsers() {
        List<User> all = this.userRepository.findAll();
        all.sort((u1, u2) -> u2.getGold().compareTo(u1.getGold()));
        java.lang.reflect.Type allUserType = new TypeToken<List<UserDashboardViewModel>>() {
        }.getType();
        return this.modelMapper.map(all, allUserType);
    }

    @Override
    public List<UserAdminViewModel> getAllUsersWithRoles() {
        List<User> all = this.userRepository.findAll();
        all.sort((u1, u2) -> u2.getGold().compareTo(u1.getGold()));
        java.lang.reflect.Type allUserType = new TypeToken<List<UserAdminViewModel>>() {
        }.getType();
        return this.modelMapper.map(all, allUserType);
    }

    @Override
    public User findFirstById(String id) {
        return this.userRepository.findFirstById(id);
    }

    @Override
    public List<UserDashboardViewModel> getChallengesSent(String userEmail) {
        User firstByEmail = this.userRepository.findFirstByEmail(userEmail);
        List<User> challengesSend = firstByEmail.getChallengesSend();
        challengesSend.sort((u1, u2) -> u2.getGold().compareTo(u1.getGold()));
        java.lang.reflect.Type targetListType = new TypeToken<List<UserDashboardViewModel>>() {
        }.getType();
        return this.modelMapper.map(challengesSend, targetListType);
    }

    @Override
    public List<UserDashboardViewModel> getChallengesPending(String userEmail) {
        User firstByEmail = this.userRepository.findFirstByEmail(userEmail);
        List<User> challengesPending = firstByEmail.getChallengesPending();
        challengesPending.sort((u1, u2) -> u2.getGold().compareTo(u1.getGold()));
        java.lang.reflect.Type targetListType = new TypeToken<List<UserDashboardViewModel>>() {
        }.getType();
        return this.modelMapper.map(challengesPending, targetListType);
    }

    @Override
    public List<UserDashboardViewModel> getChallengesAccepted(String userEmail) {
        User firstByEmail = this.userRepository.findFirstByEmail(userEmail);
        List<User> challengesAccepted = firstByEmail.getChallengesAccepted();
        challengesAccepted.sort((u1, u2) -> u2.getGold().compareTo(u1.getGold()));
        java.lang.reflect.Type targetListType = new TypeToken<List<UserDashboardViewModel>>() {
        }.getType();
        return this.modelMapper.map(challengesAccepted, targetListType);
    }

    @Override
    public UserDashboardViewModel sendChallenge(String id, String userEmail) {
        User currentUser = this.userRepository.findFirstByEmail(userEmail);
        User challenged = this.userRepository.findFirstById(id);
        //Challenger side CHALLENGES SEND
        List<User> currentUserChallengesSend = currentUser.getChallengesSend();
        currentUserChallengesSend.add(challenged);
        currentUser.setChallengesSend(currentUserChallengesSend);
        //User that is challenged PENDING side
        List<User> challengesPending = challenged.getChallengesPending();
        challengesPending.add(currentUser);
        challenged.setChallengesPending(challengesPending);
        //Save
        this.userRepository.save(challenged);
        this.userRepository.save(currentUser);
        //Return model
        return this.modelMapper.map(currentUser,UserDashboardViewModel.class);
    }

    @Override
    public UserDashboardViewModel approveChallenge(String id, String userEmail) {
        User currentUser = this.userRepository.findFirstByEmail(userEmail);
        User challenger = currentUser.getChallengesPending().stream()
                .filter(u -> id.equals(u.getId()))
                .findFirst()
                .orElse(null);
        if (challenger != null) {
            //Remove from pending/sent
            currentUser.getChallengesPending().remove(challenger);
            challenger.getChallengesSend().remove(currentUser);
            //Current user add approved
            List<User> currentUserChallengesAccepted = currentUser.getChallengesAccepted();
            currentUserChallengesAccepted.add(challenger);
            currentUser.setChallengesAccepted(currentUserChallengesAccepted);
            //Challenger add approved
            List<User> challengerChallengesAccepted = challenger.getChallengesAccepted();
            challengerChallengesAccepted.add(currentUser);
            challenger.setChallengesAccepted(challengerChallengesAccepted);
            //Save and return
            this.userRepository.save(challenger);
            this.userRepository.save(currentUser);
            return this.modelMapper.map(currentUser,UserDashboardViewModel.class);
        } else {
            return null;
        }
    }

    @Override
    public UserDashboardViewModel cancelChallenge(String id, String userEmail) {
        User currentUser = this.userRepository.findFirstByEmail(userEmail);
        User challenger = currentUser.getChallengesSend().stream()
                .filter(u -> id.equals(u.getId()))
                .findFirst()
                .orElse(null);
        if (challenger != null) {
            currentUser.getChallengesSend().remove(challenger);
            challenger.getChallengesPending().remove(currentUser);
            this.userRepository.save(challenger);
            this.userRepository.save(currentUser);
            return this.modelMapper.map(currentUser,UserDashboardViewModel.class);
        } else {
            return null;
        }
    }

    @Override
    public UserDashboardViewModel declineChallenge(String id, String userEmail) {
        User currentUser = this.userRepository.findFirstByEmail(userEmail);
        User challenger = currentUser.getChallengesPending().stream()
                .filter(u -> id.equals(u.getId()))
                .findFirst()
                .orElse(null);
        if (challenger != null) {
            currentUser.getChallengesPending().remove(challenger);
            challenger.getChallengesSend().remove(currentUser);
            this.userRepository.save(currentUser);
            this.userRepository.save(challenger);
            return this.modelMapper.map(currentUser, UserDashboardViewModel.class);
        } else {
            return null;
        }
    }

    @Override
    public List<UserDashboardViewModel> getAllUserAvailableToChallenge(String userEmail) {
        List<User> all = this.userRepository.findAll();
        User firstByEmail = this.userRepository.findFirstByEmail(userEmail);
        all.removeAll(firstByEmail.getChallengesPending());
        all.removeAll(firstByEmail.getChallengesSend());
        all.removeAll(firstByEmail.getChallengesAccepted());
        all.sort((u1, u2) -> u2.getGold().compareTo(u1.getGold()));
        java.lang.reflect.Type allUserType = new TypeToken<List<UserDashboardViewModel>>() {
        }.getType();
        return this.modelMapper.map(all, allUserType);
    }

    @Override
    public UserAdminViewModel getUserById(String id) {
        return this.modelMapper.map(this.userRepository.findFirstById(id),UserAdminViewModel.class);
    }

    @Override
    public UserAdminViewModel saveUserEdit(UserAdminViewModel userAdminViewModel) {
        User mappedToEntity = this.modelMapper.map(userAdminViewModel, User.class);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        User firstById = this.userRepository.findFirstById(userAdminViewModel.getId());
        this.modelMapper.map(mappedToEntity, firstById);
        User saved = this.userRepository.save(firstById);
        return this.modelMapper.map(saved, UserAdminViewModel.class);
    }
}
