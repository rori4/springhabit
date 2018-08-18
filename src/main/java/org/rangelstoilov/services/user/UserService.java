package org.rangelstoilov.services.user;

import org.rangelstoilov.entities.User;
import org.rangelstoilov.models.view.UserRegisterRequestModel;

public interface UserService {
    User register(UserRegisterRequestModel model);
    User findUserEntityByEmail(String email);
}
