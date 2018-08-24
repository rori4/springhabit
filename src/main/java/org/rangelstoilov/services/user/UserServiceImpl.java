package org.rangelstoilov.services.user;

import org.modelmapper.ModelMapper;
import org.rangelstoilov.entities.Role;
import org.rangelstoilov.entities.User;
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

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private static final int BASE_REWARD_MULTIPLIER = 100;
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
        this.roleService.addUserAndAdminRoleIfNotExistant();
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
        return modelMapper.map(userRepository.findFirstByEmail(email),UserDashboardViewModel.class);
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
    public UserRewardModel rewardUserForTaskDone(String email, Integer streakMultiplier){
        UserRewardModel userRewardModel = new UserRewardModel();
        User user = this.userRepository.findFirstByEmail(email);

        //This will determine the exp reward multiplier
        Integer rewardMultiplier = BASE_REWARD_MULTIPLIER*user.getLevel();

        //Possible rewards
        Integer exp = rewardMultiplier*streakMultiplier;
        Integer gold = rewardMultiplier/2*streakMultiplier;

        //Setting the user reward model values
        userRewardModel.setExperience(exp);
        userRewardModel.setGold(gold);

        //When leveling up
        if(user.getExperience()+exp >= user.getNextLevelExp()){
            userRewardModel.setLevel(1);
            userRewardModel.setHealth(user.getMaxHealth());
            user.levelUp();
            //Setting user entity valus
        }
        user.setExperience(user.getExperience()+exp);
        user.setGold(user.getGold()+gold);

        this.userRepository.save(user);
        return userRewardModel;
    }

    @Override
    public UserRewardModel damageUserForTaskNotDone(String email, Integer streakMultiplier) {
        UserRewardModel userRewardModel = new UserRewardModel();
        User user = this.userRepository.findFirstByEmail(email);
        Integer damage = user.getMaxHealth()/20;
        userRewardModel.setHealth(-damage);
        user.setHealth(user.getHealth()-damage);
        if (user.getHealth()-damage<=0){
            userRewardModel.setLevel(-1);
            user.dead();
        }
        this.userRepository.save(user);
        return userRewardModel;
    }

}
