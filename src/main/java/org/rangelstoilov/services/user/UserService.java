package org.rangelstoilov.services.user;

import org.rangelstoilov.entities.User;
import org.rangelstoilov.model.view.UserRegisterRequestModel;

public interface UserService {
    User register(UserRegisterRequestModel model);
}
