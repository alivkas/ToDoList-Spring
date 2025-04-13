package com.example.todolistspring.store.entities;

import com.example.todolistspring.store.entities.TaskEntity;
import com.example.todolistspring.store.entities.base.BasicEntity;
import jakarta.persistence.*;

/**
 * Сущность тега
 */
@Entity
@Table(name = "tbl_tag")
public class TagEntity extends BasicEntity {

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private TaskEntity task;

    /**
     * Конструктор инициализации
     */
    public TagEntity() {
    }

    /**
     * Конструктор создание сущности
     * @param name название
     */
    public TagEntity(String name) {
        this.name = name;
    }

    /**
     * Получить название
     * @return название
     */
    public String getName() {
        return name;
    }

    /**
     * Устнавить название
     * @param name название
     */
    public void setName(String name) {
        this.name = name;
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
     * @param task зададние
     */
    public void setTask(TaskEntity task) {
        this.task = task;
    }
}
