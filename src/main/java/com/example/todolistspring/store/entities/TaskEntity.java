package com.example.todolistspring.store.entities;

import com.example.todolistspring.store.entities.base.BasicEntity;
import com.example.todolistspring.store.enums.TaskPriority;
import com.example.todolistspring.store.enums.TaskStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Сущность задания
 */
@Entity
@Table(name = "tbl_task")
public class TaskEntity extends BasicEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private TaskPriority priority;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private Set<TagEntity> tags = new HashSet<>();

    @OneToOne(mappedBy = "task", cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id")
    private CommentEntity comment;

    @OneToOne(mappedBy = "task", cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    /**
     * Конструктор инициализации
     */
    public TaskEntity() {
    }

    /**
     * Конструктор создания сущности
     * @param title название
     * @param description описание
     * @param createdAt время создания
     * @param status статус
     * @param priority приоритет
     */
    public TaskEntity(String title,
                      String description,
                      LocalDateTime createdAt,
                      TaskStatus status,
                      TaskPriority priority) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.status = status;
        this.priority = priority;
    }

    /**
     * Получить название
     * @return название
     */
    public String getTitle() {
        return title;
    }

    /**
     * Установить название
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Получить описание
     * @return описание
     */
    public String getDescription() {
        return description;
    }

    /**
     * Установить описание
     * @param description описание
     */
    public void setDescription(String description) {
        this.description = description;
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
     * Получить статус
     * @return статус
     */
    public TaskStatus getStatus() {
        return status;
    }

    /**
     * Установить статус
     * @param status статус
     */
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    /**
     * Получить приоритет
     * @return приоритет
     */
    public TaskPriority getPriority() {
        return priority;
    }

    /**
     * Установить приоритет
     * @param priority приоритет
     */
    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    /**
     * Получить пользователя
     * @return пользователь
     */
    public UserEntity getUser() {
        return user;
    }

    /**
     * Установить пользователя
     * @param user пользователь
     */
    public void setUser(UserEntity user) {
        this.user = user;
    }

    /**
     * Получить теги
     * @return теги
     */
    public Set<TagEntity> getTags() {
        return tags;
    }

    /**
     * Установить теги
     * @param tags теги
     */
    public void setTags(Set<TagEntity> tags) {
        this.tags = tags;
    }

    /**
     * Получить комментарий
     * @return комментарий
     */
    public CommentEntity getComment() {
        return comment;
    }

    /**
     * Установить комментарий
     * @param comment комментарий
     */
    public void setComment(CommentEntity comment) {
        this.comment = comment;
    }

    /**
     * Получить проект
     * @return проект
     */
    public ProjectEntity getProject() {
        return project;
    }

    /**
     * Установить проект
     * @param project
     */
    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    /**
     * Получить дату и время дедлайна задачи
     *
     * @return дата и время, к которому должна быть выполнена задача, или null, если дедлайн не установлен
     */
    public LocalDateTime getDeadline() {
        return deadline;
    }

    /**
     * Установить дату и время дедлайна задачи
     *
     * @param deadline дата и время, к которому должна быть выполнена задача
     */
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
