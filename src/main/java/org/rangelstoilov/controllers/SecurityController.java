package org.rangelstoilov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SecurityController extends BaseController {

    @GetMapping("/login")
    public ModelAndView login() {
        return this.view("/users/login");
    }
}
