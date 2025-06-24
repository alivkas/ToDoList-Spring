package com.example.todolistspring.api.exceptions.advice;

import com.example.todolistspring.api.exceptions.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Отлавливание всех исключений
 */
@ControllerAdvice
public class AllExceptionHandler {

    /**
     * Отловить UserAlreadyExistException
     * @param e ошибка UserAlreadyExistException
     * @param model модель представления
     * @return шаблон с ошибкой
     */
    @ExceptionHandler(UserAlreadyExistException.class)
    public String handleUserAlreadyExistException(UserAlreadyExistException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "register";
    }

    /**
     * Отловить Exception
     * @param e ошибка Exception
     * @param model модель представления
     * @return шаблон с ошибкой
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("error", "Произошла ошибка " + e.getMessage());
        return "error";
    }

    /**
     * Отловить UserNotFoundException
     * @param e ошибка UserNotFoundException
     * @param model модель представления
     * @return шаблон с ошибкой
     */
    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotExistException(UserNotFoundException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "register";
    }

    /**
     * Отловить TaskNotFoundException
     * @param e ошибка TaskNotFoundException
     * @param model модель представления
     * @return шаблон с ошибкой
     */
    @ExceptionHandler(TaskNotFoundException.class)
    public String handleTaskNotFoundException(TaskNotFoundException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "main"; //TODO вставить шаблон, где будет применяться эта ошибка
    }

    /**
     * Отловить NotOwnerException
     * @param e ошибка NotOwnerException
     * @param model модель представления
     * @return шаблон с ошибкой
     */
    @ExceptionHandler(NotOwnerException.class)
    public String handleNotOwnerException(NotOwnerException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "main"; //TODO вставить шаблон, где будет применяться эта ошибка
    }
}
