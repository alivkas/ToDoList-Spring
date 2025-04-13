package com.example.todolistspring.store.entities;

import com.example.todolistspring.store.entities.base.BasicEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Сущность проекта
 */
@Entity
@Table(name = "tbl_project")
public class ProjectEntity extends BasicEntity {

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

    @OneToOne
    private TaskEntity task;

    /**
     * Конструктор инициализации
     */
    public ProjectEntity() {
    }

    /**
     * Конструктор создания сущности
     * @param name название
     * @param description описание
     */
    public ProjectEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Получить название
     * @return название
     */
    public String getName() {
        return name;
    }

    /**
     * Устновить название
     * @param name название
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Получить описание
     * @return описание
     */
    public String getDescription() {
        return description;
    }

    /**
     * Устновить описание
     * @param description описание
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Получить задание
     * @return задание
     */
    public TaskEntity getTask() {
        return task;
    }

    /**
     * Установить задание
     * @param task задание
     */
    public void setTask(TaskEntity task) {
        this.task = task;
    }
}
