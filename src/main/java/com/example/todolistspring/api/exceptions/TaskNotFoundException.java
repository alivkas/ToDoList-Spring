package com.example.todolistspring.api.exceptions;

/**
 * Исключение, когда задачи не найдено
 */
public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(String title) {
        super("Задание " + title + " не найдено");
    }

    public TaskNotFoundException(Long id) {
        super("Задание " + id + " не найдено");
    }
}
