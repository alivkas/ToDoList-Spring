package com.example.todolistspring.api.configuration;

import com.example.todolistspring.store.entities.RoleEntity;
import com.example.todolistspring.store.entities.UserEntity;
import com.example.todolistspring.store.enums.UserRole;
import com.example.todolistspring.store.repositories.RoleRepository;
import com.example.todolistspring.store.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

/**
 * Конфигурация админа
 */
@Configuration
@DependsOn("roleConfig")
public class AdminConfig {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminEmail;
    private final String adminPassword;
    private final String adminUsername;

    /**
     * Конструктор для внедрения UserRepository, PasswordEncoder, RoleRepository
     * @param userRepository репозиторий пользователя
     * @param passwordEncoder кодировщик пароля
     */
    @Autowired
    public AdminConfig(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository,
                       @Value("${admin.email}") String adminEmail,
                       @Value("${admin.password}") String adminPassword,
                       @Value("${admin.username}") String adminUsername) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.adminUsername = adminUsername;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }

    /**
     * Создать админа при запуске приложения
     */
    @Transactional
    @PostConstruct
    public void createAdmin() {
        if (!userRepository.existsByEmail(adminEmail)) {
            UserEntity user = new UserEntity(
                    adminUsername,
                    passwordEncoder.encode(adminPassword),
                    adminEmail
            );
            RoleEntity role = roleRepository.findByRole(UserRole.ROLE_ADMIN);
            user.setRoles(Set.of(role));

            userRepository.save(user);
        }
    }
}
