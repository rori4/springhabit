package org.rangelstoilov.controllers;

import org.rangelstoilov.models.view.user.UserRegisterModel;
import org.rangelstoilov.services.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController extends BaseController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return this.view("/users/register", "user", new UserRegisterModel());
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute("user") @Valid UserRegisterModel model, BindingResult result) {
        if (!result.hasErrors()) {
            userService.register(model);
        } else if (result.hasErrors()) {
            return new ModelAndView("users/register", "user", model);
        }
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/forgotten")
    public ModelAndView forgotten() {
        return new ModelAndView("/users/forgotten");
    }
}
