package com.example.todolistspring.store.repositories;

import com.example.todolistspring.store.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий проекта
 */
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
}
