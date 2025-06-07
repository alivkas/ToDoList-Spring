package com.example.todolistspring.api.exceptions;

/**
 * Исключение, когда пользователь не владелец таска
 */
public class NotOwnerException extends RuntimeException {

    public NotOwnerException(String username, String title) {
        super("Пользователь (%s) не является владельцем задания (%s)"
                .formatted(username, title));
    }
}
