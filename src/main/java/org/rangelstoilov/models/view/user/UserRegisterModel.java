package org.rangelstoilov.models.view.user;


import org.rangelstoilov.custom.annotations.EmailUnique;
import org.rangelstoilov.custom.annotations.ValidEmail;
import org.rangelstoilov.custom.annotations.ValidPassword;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserRegisterModel {

    @NotNull(message = "Please provide an email")
    @NotEmpty(message = "Email can not be empty")
    @ValidEmail(message = "Please enter a valid email")
    @EmailUnique(message = "Email already exists in database")
    private String email;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    @ValidPassword(message = "The password must be between 8 and 30 symbols and contain one upper-case, one lower-case and one digit (no whitespaces)")
    private String password;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
