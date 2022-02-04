package com.dwu.alonealong.controller;

import com.dwu.alonealong.exception.NullProductException;
import com.dwu.alonealong.exception.UserNotMatchException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionController{
    @ExceptionHandler({ Exception.class, NullProductException.class, UserNotMatchException.class })
    public String NullProductException(Exception e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error";
    }
}
