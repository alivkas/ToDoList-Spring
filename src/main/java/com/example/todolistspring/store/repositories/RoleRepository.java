package com.example.todolistspring.store.repositories;

import com.example.todolistspring.store.entities.RoleEntity;
import com.example.todolistspring.store.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий ролей
 */
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    /**
     * Проверить существует ли роль
     * @param role роль
     * @return true - существует, false - не существует
     */
    Boolean existsByRole(UserRole role);

    /**
     * Найти роль по ее enum
     * @param role enum роли
     * @return сущность роли
     */
    RoleEntity findByRole(UserRole role);
}
