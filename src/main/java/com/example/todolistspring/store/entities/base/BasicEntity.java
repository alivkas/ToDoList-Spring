package com.example.todolistspring.store.entities.base;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * Базовая сущность для других сущностей
 */
@MappedSuperclass
public abstract class BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Получить id
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Установить id
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }
}
