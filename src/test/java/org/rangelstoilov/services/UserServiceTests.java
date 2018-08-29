package org.rangelstoilov.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rangelstoilov.entities.User;
import org.rangelstoilov.models.view.user.UserAdminViewModel;
import org.rangelstoilov.models.view.user.UserRegisterModel;
import org.rangelstoilov.repositories.UserRepository;
import org.rangelstoilov.services.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.rangelstoilov.services.user.UserServiceImpl.BASE_DMG_MULTIPLIER;
import static org.rangelstoilov.services.user.UserServiceImpl.BASE_REWARD_MULTIPLIER;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTests {

    private static final String EMAIL = "pesho@gmail.com";
    private static final String NAME = "Pesho";
    private static final String PASSWORD = "Pesho1234";
    public static final int STREAK_MULTIPLIER = 1;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;



    @Test
    public void testRegister_withEmailNameAndPassword_passwordShouldNotBeTheSameAsInput() {
        UserRegisterModel user = new UserRegisterModel();
        user.setEmail(EMAIL);
        user.setName(NAME);
        user.setPassword(PASSWORD);

        User result = this.userService.register(user);

        assertNotSame(
                "Password was saved the same in database!",
                PASSWORD,
                result.getPassword()
        );
    }

    @Test
    public void testUserExists_withTwoUsers_userShouldExistInDataBase() {
        boolean userExists = this.userService.userExists(EMAIL);

        assertTrue(
                "User does not exist!",
                userExists
        );
    }

    @Test
    public void testRewardUserForTaskDone_withEmailAndMultiplier_userShouldBeRewarded() {
        User register = this.userRepository.findFirstByEmail(EMAIL);
        this.userService.rewardUserForTaskDone(EMAIL, STREAK_MULTIPLIER);
        UserAdminViewModel rewardedUser = this.userService.getUserById(register.getId());
        int reward = (BASE_REWARD_MULTIPLIER * register.getLevel())/2 * STREAK_MULTIPLIER;
        int userGoldExpected = register.getGold() + reward;
        int actual = rewardedUser.getGold();

        assertEquals (
                "User was not rewarded properly!",
                userGoldExpected,
                actual
        );
    }

    @Test
    public void testDamageUserForTaskNotDone_withEmailAndMultiplier_userShouldBeDamaged() {
        User register = this.userRepository.findFirstByEmail(EMAIL);
        this.userService.damageUserForTaskNotDone(EMAIL, STREAK_MULTIPLIER);
        UserAdminViewModel damagedUser = this.userService.getUserById(register.getId());
        int dmgDone = register.getMaxHealth() / BASE_DMG_MULTIPLIER;
        int healthExpected = register.getHealth()-dmgDone;
        int actual = damagedUser.getHealth();

        assertEquals (
                "User was not rewarded properly!",
                healthExpected,
                actual
        );
    }

    @Test
    public void testSendChallenge_withIdAndUserEmail_userHaveChallengeSent() {
        User currentUser = this.userRepository.findFirstByEmail(EMAIL);
        UserRegisterModel tosho = new UserRegisterModel();
        tosho.setEmail("tosho@gmail.com");
        tosho.setName("tosho");
        tosho.setPassword("Tosho1234");
        User opponent = this.userService.register(tosho);
        this.userService.sendChallenge(opponent.getId(), currentUser.getEmail());
        List<User> sentChallenges = currentUser.getChallengesSend();
        boolean result = sentChallenges.stream().anyMatch(u -> u.getId().equals(opponent.getId()));
        assertTrue("Opponent should be in sentChallenges",result);
    }

}
