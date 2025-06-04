package com.example.todolistspring.api.configuration;

import com.example.todolistspring.store.entities.RoleEntity;
import com.example.todolistspring.store.entities.UserEntity;
import com.example.todolistspring.store.enums.UserRole;
import com.example.todolistspring.store.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

/**
 * Конфигурация админа
 */
@Configuration
public class AdminConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String adminEmail;
    @Value("${admin.password}")
    private String adminPassword;
    @Value("${admin.username}")
    private String adminUsername;

    /**
     * Конструктор для внедрения UserRepository, PasswordEncoder
     * @param userRepository репозиторий пользователя
     * @param passwordEncoder кодировщик пароля
     */
    @Autowired
    public AdminConfig(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Создать админа при запуске приложения
     */
    @PostConstruct
    public void createAdmin() {
        if (userRepository.existsByEmail(adminEmail)) {
            return;
        }
        UserEntity user = new UserEntity(
                adminUsername,
                passwordEncoder.encode(adminPassword),
                adminEmail
        );
        RoleEntity role = new RoleEntity(UserRole.ROLE_ADMIN);
        user.setRoles(Set.of(role));

        userRepository.save(user);
    }
}
