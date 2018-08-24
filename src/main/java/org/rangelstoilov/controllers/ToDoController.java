package org.rangelstoilov.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@PreAuthorize("isAuthenticated()")
public class ToDoController extends BaseController {

    @GetMapping("/todo/details")
    public ModelAndView todoDetails(@RequestParam String id){
        return this.view("task/todoDetails");
    }
}
