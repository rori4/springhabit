package org.rangelstoilov.controllers;

import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController {

    protected ModelAndView view(String view) {
        ModelAndView modelAndView = new ModelAndView(view);
        return modelAndView;
    }

    protected ModelAndView
    view(String view, String name, Object model) {
        ModelAndView modelAndView = new ModelAndView(view);
        modelAndView.addObject(name, model);
        return modelAndView;
    }


}
