package com.example.todolistspring.store.repositories;

import com.example.todolistspring.store.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий пользователя
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Получить пользователя по имени
     * @param username имя пользователя
     * @return сущность пользователя, если он есть
     */
    Optional<UserEntity> findByUsername(String username);
}
