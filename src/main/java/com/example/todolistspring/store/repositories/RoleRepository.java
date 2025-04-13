package com.example.todolistspring.store.repositories;

import com.example.todolistspring.store.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий ролей
 */
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
