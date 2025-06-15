package com.example.todolistspring.store.entities;

import com.example.todolistspring.store.entities.base.BasicEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/**
 * Сущность комментария
 */
@Entity
@Table(name = "tbl_comment", schema = "public")
public class CommentEntity extends BasicEntity {

    @Column(name = "text")
    private String text;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToOne
    private TaskEntity task;

    /**
     * Конструктор инициализации
     */
    public CommentEntity() {
    }

    /**
     * Конструктор создания сущности
     * @param text текст
     * @param createdAt время создания
     */
    public CommentEntity(String text, LocalDateTime createdAt) {
        this.text = text;
        this.createdAt = createdAt;
    }

    /**
     * Получить текст
     * @return текст
     */
    public String getText() {
        return text;
    }

    /**
     * Установить текст
     * @param text текст
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Получить время создания
     * @return время создания
     */
   public LocalDateTime getCreatedAt() {
       return createdAt;
   }

    /**
     * Установить время создания
     * @param createdAt время создания
     */
   public void setCreatedAt(LocalDateTime createdAt) {
       this.createdAt = createdAt;
   }

    /**
     * Получить задание
     * @return задание
     */
   public TaskEntity getTask() {
       return task;
   }

    /**
     * Устнвоить задание
     * @param task задание
     */
   public void setTask(TaskEntity task) {
       this.task = task;
   }
}
