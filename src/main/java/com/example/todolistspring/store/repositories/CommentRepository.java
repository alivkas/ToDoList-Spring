package com.example.todolistspring.store.repositories;

import com.example.todolistspring.store.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий комментариев
 */
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
