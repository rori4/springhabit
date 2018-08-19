package org.rangelstoilov.services.user;

import org.rangelstoilov.custom.exceptions.EmailExistsException;
import org.rangelstoilov.entities.User;
import org.rangelstoilov.models.view.UserRegisterRequestModel;

public interface UserService {
    User register(UserRegisterRequestModel model) throws EmailExistsException;
    User findUserEntityByEmail(String email);
}
