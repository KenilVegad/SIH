package com.example.cricketbooking.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolation(DataIntegrityViolationException ex,
                                               HttpServletRequest request,
                                               Model model) {
        model.addAttribute("error", "Operation failed because the record is still in use");
        model.addAttribute("path", request.getRequestURI());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex,
                                         HttpServletRequest request,
                                         Model model) {
        model.addAttribute("error", ex.getMessage() != null ? ex.getMessage() : "Unexpected server error");
        model.addAttribute("path", request.getRequestURI());
        return "error";
    }
}
