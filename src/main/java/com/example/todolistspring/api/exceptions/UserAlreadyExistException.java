package com.example.todolistspring.api.exceptions;

/**
 * Исключение, когда пользователь уже существует
 */
public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException(String email) {
        super("Пользователь с почтой " + email + " уже сущестует");
    }
}
