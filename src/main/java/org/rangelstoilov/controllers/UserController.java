package org.rangelstoilov.controllers;

import org.rangelstoilov.models.view.UserRegisterRequestModel;
import org.rangelstoilov.services.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController extends BaseController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return  this.view("/users/register");
    }

    @PostMapping("/register")
    public ModelAndView register(UserRegisterRequestModel model) {
        this.userService.register(model);
        return  new ModelAndView("redirect:/login");
    }

    @GetMapping("/forgotten")
    public ModelAndView forgotten() {
        return  new ModelAndView("/users/forgotten");
    }
}
