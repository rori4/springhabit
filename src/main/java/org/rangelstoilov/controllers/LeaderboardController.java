package org.rangelstoilov.controllers;

import org.rangelstoilov.models.view.user.UserDashboardViewModel;
import org.rangelstoilov.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@PreAuthorize("isAuthenticated()")
public class LeaderboardController extends BaseController{
    private final UserService userService;

    @Autowired
    public LeaderboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/leaderboard/users")
    public ModelAndView rankings() {
        List<UserDashboardViewModel> allUsers = this.userService.getAllUsers();
        return this.view("/leaderboard/users", "users", allUsers);
    }
}
