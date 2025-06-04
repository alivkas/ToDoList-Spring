package com.example.todolistspring.api.exceptions.advice;

import com.example.todolistspring.api.exceptions.UserAlreadyExistException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Отлавливание всех исключений
 */
@ControllerAdvice
public class AllExceptionHandler {

    /**
     * Отловить UserAlreadyExistException
     * @param e ошибка UserAlreadyExistException
     * @return шаблон с ошибкой
     */
    @ExceptionHandler(UserAlreadyExistException.class)
    public String handleUserAlreadyExistException(UserAlreadyExistException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "register";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("error", "Произошла ошибка " + e.getMessage());
        return "error";
    }
}
