package com.example.todolistspring.store.repositories;

import com.example.todolistspring.store.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий тегов
 */
public interface TagRepository extends JpaRepository<TagEntity, Long> {
}
