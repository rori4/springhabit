package org.rangelstoilov.services.user;

import org.rangelstoilov.models.view.user.UserDashboardViewModel;
import org.rangelstoilov.models.view.user.UserRegisterModel;

public interface UserService {
    boolean register(UserRegisterModel model);
    boolean userExists(String username);
    UserDashboardViewModel getUserDashboardDataByEmail(String email);
}
