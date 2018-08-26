package org.rangelstoilov.controllers;

import org.rangelstoilov.models.view.user.UserDashboardViewModel;
import org.rangelstoilov.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@PreAuthorize("isAuthenticated()")
public class HomeController extends BaseController {
    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ModelAndView index(Principal principal){
        UserDashboardViewModel userDashboardDataByEmail = userService.getUserDashboardDataByEmail(principal.getName());
        return this.view("home/index", "user", userDashboardDataByEmail);
    }
}
