package org.rangelstoilov.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestSecurityController {

    @GetMapping("/unauth")
    public @ResponseBody String unAuthMethod(){
        return "I am unauth method";
    }

    @GetMapping("/auth")
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody String authMethod(){
        return "I am auth method";
    }

    @GetMapping("/for_users")
    @PreAuthorize("hasRole('ROLE_USER')")
    public @ResponseBody String users(){
        return "I am auth as USER";
    }

    @GetMapping("/for_admins")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody String admins(){
        return "I am auth as ADMIN";
    }
}
