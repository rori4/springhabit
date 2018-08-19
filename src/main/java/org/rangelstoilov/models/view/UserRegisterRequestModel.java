package org.rangelstoilov.models.view;


import org.rangelstoilov.custom.annotations.EmailUnique;
import org.rangelstoilov.custom.annotations.ValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserRegisterRequestModel {

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
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must contain ....")
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
