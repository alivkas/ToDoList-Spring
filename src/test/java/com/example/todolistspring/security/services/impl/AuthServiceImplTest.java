package com.example.todolistspring.security.services.impl;

import com.example.todolistspring.api.dto.UserDto;
import com.example.todolistspring.api.exceptions.UserAlreadyExistException;
import com.example.todolistspring.mapper.UserMapper;
import com.example.todolistspring.store.entities.RoleEntity;
import com.example.todolistspring.store.entities.UserEntity;
import com.example.todolistspring.store.enums.UserRole;
import com.example.todolistspring.store.repositories.RoleRepository;
import com.example.todolistspring.store.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Тесты для сервиса авторизации {@link AuthServiceImpl}
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private UserMapper userMapper;
    @Mock private RoleRepository roleRepository;

    @InjectMocks private AuthServiceImpl authService;

    /**
     * Проверяет успешное создание нового пользователя
     * Ожидается, что если email не занят и роль найдена, пользователь будет сохранён в репозитории
     * и ему будет назначена роль ROLE_USER
     */
    @Test
    void createUser_NewUser_Success() {
        UserDto dto = new UserDto(
                "testUser",
                "password",
                "password",
                "test@mail.ru",
                List.of(),
                List.of()
        );
        UserEntity userEntity = new UserEntity();
        RoleEntity roleEntity = new RoleEntity();

        when(userRepository.existsByEmail(dto.email())).thenReturn(false);
        when(userMapper.toEntity(dto, passwordEncoder)).thenReturn(userEntity);
        when(roleRepository.findByRole(UserRole.ROLE_USER)).thenReturn(roleEntity);

        authService.createUser(dto);

        verify(userRepository, times(1)).save(userEntity);
        assertEquals(Set.of(roleEntity), userEntity.getRoles());
    }

    /**
     * Проверяет ситуацию, когда пользователь с таким email уже существует
     * Ожидается выбрасывание {@link UserAlreadyExistException} и отсутствие попытки сохранения пользователя
     */
    @Test
    void createUser_ExistingEmail_ThrowsException() {
        UserDto dto = new UserDto(
                "testUser",
                "password",
                "password",
                "existing@mail.ru",
                List.of(),
                List.of()
        );
        when(userRepository.existsByEmail(dto.email())).thenReturn(true);

        assertThrows(UserAlreadyExistException.class,
                () -> authService.createUser(dto));
        verify(userRepository, never()).save(any());
    }

    /**
     * Проверяет ситуацию, когда роль пользователя не найдена в базе данных
     * Ожидается выбрасывание {@link IllegalStateException}
     */
    @Test
    void createUser_RoleNotFound_ThrowsException() {
        UserDto dto = new UserDto(
                "testUser",
                "password",
                "password",
                "test@mail.ru",
                List.of(),
                List.of()
        );
        when(userRepository.existsByEmail(dto.email())).thenReturn(false);
        when(userMapper.toEntity(dto, passwordEncoder)).thenReturn(new UserEntity());
        when(roleRepository.findByRole(UserRole.ROLE_USER)).thenReturn(null);

        assertThrows(IllegalStateException.class,
                () -> authService.createUser(dto));
    }
}
