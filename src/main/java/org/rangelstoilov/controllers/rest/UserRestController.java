package org.rangelstoilov.controllers.rest;

import com.google.gson.Gson;
import org.rangelstoilov.models.view.user.UserDashboardViewModel;
import org.rangelstoilov.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@PreAuthorize("isAuthenticated()")
public class UserRestController {
    private final Gson gson;
    private final UserService userService;

    @Autowired
    public UserRestController(Gson gson, UserService userService) {
        this.gson = gson;
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public ResponseEntity<?> getUserData(Principal principal) {
        UserDashboardViewModel userDashboardDataByEmail = userService.getUserDashboardDataByEmail(principal.getName());
        return new ResponseEntity<>(userDashboardDataByEmail, HttpStatus.OK);
    }
}
