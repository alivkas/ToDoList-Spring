package com.example.todolistspring.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий проекта
 */
public interface ProjectRepository extends JpaRepository<ProjectRepository, Long> {
}
