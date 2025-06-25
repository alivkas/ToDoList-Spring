package com.example.todolistspring.security.services.impl;

import com.example.todolistspring.api.dto.UserDto;
import com.example.todolistspring.api.exceptions.UserAlreadyExistException;
import com.example.todolistspring.mapper.UserMapper;
import com.example.todolistspring.security.services.interfaces.AuthService;
import com.example.todolistspring.store.entities.RoleEntity;
import com.example.todolistspring.store.entities.UserEntity;
import com.example.todolistspring.store.enums.UserRole;
import com.example.todolistspring.store.repositories.RoleRepository;
import com.example.todolistspring.store.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Реализация сервиса авторизации
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    /**
     * Конструктор для внедрения UserMapper, UserRepository, PasswordEncoder
     * @param userRepository репозиторий пользователя
     * @param userMapper маппер пользователя
     * @param passwordEncoder кодировщик пароля
     */
    @Autowired
    public AuthServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public void createUser(UserDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new UserAlreadyExistException(dto.email());
        }

        UserEntity user = userMapper.toEntity(dto, passwordEncoder);
        RoleEntity role = roleRepository.findByRole(UserRole.ROLE_USER);
        if (role == null) {
            throw new IllegalStateException("Роль ROLE_USER не найдена в базе данных");
        }
        user.setRoles(Set.of(role));

        userRepository.save(user);
        LOGGER.debug("Пользователь {} сохранен", user.getEmail());
    }

}
