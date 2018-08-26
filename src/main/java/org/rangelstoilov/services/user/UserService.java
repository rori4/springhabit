package org.rangelstoilov.services.user;

import org.rangelstoilov.entities.User;
import org.rangelstoilov.models.view.user.UserDashboardViewModel;
import org.rangelstoilov.models.view.user.UserRegisterModel;
import org.rangelstoilov.models.view.user.UserRewardModel;

import java.util.List;

public interface UserService {
    boolean register(UserRegisterModel model);
    boolean userExists(String username);
    UserDashboardViewModel getUserDashboardDataByEmail(String email);
    UserRewardModel rewardUserForTaskDone(String email, Integer streakMultiplier);

    UserRewardModel damageUserForTaskNotDone(String email, Integer streakMultiplier);

    User findFirstByEmail(String userEmail);

    List<UserDashboardViewModel> getAllUsers();

    User findFirstById(String id);
}
