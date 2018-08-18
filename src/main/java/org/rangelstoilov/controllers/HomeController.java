package org.rangelstoilov.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends BaseController {

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView index(){
        return this.view("home/index");
    }
}
