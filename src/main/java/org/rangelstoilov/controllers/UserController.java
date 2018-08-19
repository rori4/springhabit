package org.rangelstoilov.controllers;

import org.rangelstoilov.custom.exceptions.EmailExistsException;
import org.rangelstoilov.entities.User;
import org.rangelstoilov.models.view.UserRegisterRequestModel;
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
      return this.view("/users/register","user",new UserRegisterRequestModel());
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute("user") @Valid UserRegisterRequestModel model, BindingResult result) {
        User registered = new User();
        if (!result.hasErrors()) {
            registered = createUserAccount(model, result);
        }
        if (registered == null) {
            result.rejectValue("email", "There is an account with that email address");
        }
        if (result.hasErrors()) {
            return new ModelAndView("users/register", "user", model);
        }
        else {
            return new ModelAndView("redirect:/login");
        }

    }

    @GetMapping("/forgotten")
    public ModelAndView forgotten() {
        return  new ModelAndView("/users/forgotten");
    }

    private User createUserAccount(UserRegisterRequestModel model, BindingResult result) {
        User registered = null;
        try {
            registered = userService.register(model);
        } catch (EmailExistsException e) {
            return null;
        }
        return registered;
    }
}
