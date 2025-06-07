package com.example.todolistspring.security.services.interfaces;

import com.example.todolistspring.api.dto.UserDto;

/**
 * Сервис авторизации
 */
public interface AuthService {

    /**
     * Создание пользователя
     * @param dto дто пользователя
     */
    void createUser(UserDto dto);
}
