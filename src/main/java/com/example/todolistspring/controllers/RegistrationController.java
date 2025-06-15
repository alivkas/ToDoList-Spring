package com.example.todolistspring.controllers;

import com.example.todolistspring.api.dto.UserDto;
import com.example.todolistspring.api.exceptions.UserAlreadyExistException;
import com.example.todolistspring.security.services.interfaces.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для обработки регистрации через форму
 */
@Controller
@Validated
public class RegistrationController {

    private final AuthService authService;

    @Autowired
    public RegistrationController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserDto("", "", "", "", null, null));
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("userDto") @Valid UserDto userDto,
                               Model model) {

        // Проверка совпадения паролей
        if (!userDto.password().equals(userDto.passwordCheck())) {
            model.addAttribute("error", "Пароли не совпадают");
            return "registration";
        }

        try {
            authService.createUser(userDto);
        } catch (UserAlreadyExistException e) {
            model.addAttribute("error", "Пользователь с таким email уже существует");
            return "registration";
        }

        return "redirect:/login";
    }
}
