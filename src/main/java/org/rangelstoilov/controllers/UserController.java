package org.rangelstoilov.controllers;

import org.rangelstoilov.models.view.user.UserDashboardViewModel;
import org.rangelstoilov.models.view.user.UserRegisterModel;
import org.rangelstoilov.services.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@PreAuthorize("isAuthenticated()")
public class UserController extends BaseController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register() {
        return this.view("/users/register", "user", new UserRegisterModel());
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(@ModelAttribute("user") @Valid UserRegisterModel model, BindingResult result) {
        if (!result.hasErrors()) {
            userService.register(model);
        } else if (result.hasErrors()) {
            return new ModelAndView("users/register", "user", model);
        }
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/forgotten")
    @PreAuthorize("isAnonymous()")
    public ModelAndView forgotten() {
        return new ModelAndView("/users/forgotten");
    }

    //API endpoints

    @GetMapping(value = "/api/user")
    @ResponseBody
    public ResponseEntity<?> getUserData(Principal principal) {
        UserDashboardViewModel userDashboardDataByEmail = userService.getUserDashboardDataByEmail(principal.getName());
        return new ResponseEntity<>(userDashboardDataByEmail, HttpStatus.OK);
    }
}
