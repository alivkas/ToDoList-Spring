package com.example.todolistspring.security.services.impl;

import com.example.todolistspring.store.entities.UserEntity;
import com.example.todolistspring.store.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Модульные тесты для сервиса {@link UserDetailsServiceImpl}
 */
@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock private UserRepository userRepository;
    @InjectMocks private UserDetailsServiceImpl userDetailsService;

    /**
     * Проверяет, что при наличии пользователя с заданным именем
     * сервис возвращает корректную сущность пользователя
     */
    @Test
    void loadUserByUsername_UserExists_ReturnsUserDetails() {
        String username = "validUser";
        UserEntity userEntity = new UserEntity();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));

        var result = userDetailsService.loadUserByUsername(username);

        assertEquals(userEntity, result);
    }

    /**
     * Проверяет, что при отсутствии пользователя с заданным именем
     * сервис выбрасывает {@link UsernameNotFoundException}, а сообщение содержит имя пользователя
     */
    @Test
    void loadUserByUsername_UserNotFound_ThrowsException() {
        String username = "invalidUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(username)
        );
        assertTrue(exception.getMessage().contains(username));
    }
}
