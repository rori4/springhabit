package org.rangelstoilov.services.user;

import org.rangelstoilov.entities.User;
import org.rangelstoilov.models.view.user.UserDashboardViewModel;
import org.rangelstoilov.models.view.user.UserRegisterModel;
import org.rangelstoilov.models.view.user.UserRewardModel;
import org.springframework.security.access.prepost.PreAuthorize;

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

    @PreAuthorize("isAuthenticated()")
    List<UserDashboardViewModel> getChallengesSent(String userEmail);

    List<UserDashboardViewModel> getChallengesPending(String userEmail);

    @PreAuthorize("isAuthenticated()")
    List<UserDashboardViewModel> getChallengesAccepted(String userEmail);

    UserDashboardViewModel sendChallenge(String id, String userEmail);

    @PreAuthorize("isAuthenticated()")
    UserDashboardViewModel approveChallenge(String id, String userEmail);

    @PreAuthorize("isAuthenticated()")
    UserDashboardViewModel cancelChallenge(String id, String userEmail);

    @PreAuthorize("isAuthenticated()")
    UserDashboardViewModel declineChallenge(String id, String userEmail);

    List<UserDashboardViewModel> getAllUserAvailableToChallenge(String userEmail);
}
