package com.example.todolistspring.api.configuration;

import com.example.todolistspring.store.entities.RoleEntity;
import com.example.todolistspring.store.enums.UserRole;
import com.example.todolistspring.store.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация ролей
 */
@Configuration
public class RoleConfig {

    private final RoleRepository roleRepository;

    /**
     * Внедрение RoleRepository
     * @param roleRepository репозиторий ролей
     */
    @Autowired
    public RoleConfig(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Инициализация ролей в базе данных
     */
    @Transactional
    @PostConstruct
    public void rolesInit() {
        if (!roleRepository.existsByRole(UserRole.ROLE_ADMIN)) {
            RoleEntity role = new RoleEntity();
            role.setRole(UserRole.ROLE_ADMIN);
            roleRepository.save(role);
        }
        if (!roleRepository.existsByRole(UserRole.ROLE_USER)) {
            RoleEntity role = new RoleEntity();
            role.setRole(UserRole.ROLE_USER);
            roleRepository.save(role);
        }
    }
}
