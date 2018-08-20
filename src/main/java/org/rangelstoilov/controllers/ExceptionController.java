package org.rangelstoilov.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class ExceptionController extends BaseController {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest request, Exception e) {
        String status = null;
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Request: " + request.getRequestURL() + " raised " + e);
        try {
            status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString();
        } catch (NullPointerException ex){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Request: " + request.getRequestURL() + " raised " + ex);
            return this.view("redirect:/login");
        }
            return this.view("error", "error", status);
    }
}
