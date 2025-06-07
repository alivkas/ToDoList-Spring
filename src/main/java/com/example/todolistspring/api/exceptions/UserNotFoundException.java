package com.example.todolistspring.api.exceptions;

/**
 * Исключение, когда пользователь не найден
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String username) {
        super("Пользователь " + username + " не найден");
    }
}
