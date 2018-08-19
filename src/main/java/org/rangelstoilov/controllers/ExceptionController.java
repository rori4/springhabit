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
    public ModelAndView handleError(HttpServletRequest request, Exception e)   {
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Request: " + request.getRequestURL() + " raised " + e);
        String status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString();
        if (status != null)
        return this.view("error","error", status);
        else
            return null;
    }
}
