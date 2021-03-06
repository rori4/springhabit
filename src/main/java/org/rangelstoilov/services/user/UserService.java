package org.rangelstoilov.services.user;

import org.rangelstoilov.entities.User;
import org.rangelstoilov.models.view.user.UserAdminViewModel;
import org.rangelstoilov.models.view.user.UserDashboardViewModel;
import org.rangelstoilov.models.view.user.UserRegisterModel;
import org.rangelstoilov.models.view.user.UserRewardModel;

import java.util.List;

public interface UserService {
    User register(UserRegisterModel model);
    boolean userExists(String username);
    UserDashboardViewModel getUserDashboardDataByEmail(String email);
    UserRewardModel rewardUserForTaskDone(String email, Integer streakMultiplier);

    UserRewardModel damageUserForTaskNotDone(String email, Integer streakMultiplier);

    User findFirstByEmail(String userEmail);

    List<UserDashboardViewModel> getAllUsers();

    List<UserAdminViewModel> getAllUsersWithRoles();

    User findFirstById(String id);

    List<UserDashboardViewModel> getChallengesSent(String userEmail);

    List<UserDashboardViewModel> getChallengesPending(String userEmail);

    List<UserDashboardViewModel> getChallengesAccepted(String userEmail);

    UserDashboardViewModel sendChallenge(String id, String userEmail);

    UserDashboardViewModel approveChallenge(String id, String userEmail);

    UserDashboardViewModel cancelChallenge(String id, String userEmail);

    UserDashboardViewModel declineChallenge(String id, String userEmail);

    List<UserDashboardViewModel> getAllUserAvailableToChallenge(String userEmail);

    UserAdminViewModel getUserById(String id);

    UserAdminViewModel saveUserEdit(UserAdminViewModel userAdminViewModel);
}
