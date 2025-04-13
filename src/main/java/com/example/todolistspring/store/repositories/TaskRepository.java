package com.example.todolistspring.store.repositories;

import com.example.todolistspring.store.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий заданий
 */
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
