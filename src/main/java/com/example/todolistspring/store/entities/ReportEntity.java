package com.example.todolistspring.store.entities;

import com.example.todolistspring.store.entities.base.BasicEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Сущность отчета
 */
@Entity
@Table(name = "tbl_report")
public class ReportEntity extends BasicEntity {

    @Column(name = "name")
    private String name;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tbl_report_completed_task",
            joinColumns = @JoinColumn(name = "report_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    private Set<TaskEntity> completedTasks = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tbl_report_overdue_task",
            joinColumns = @JoinColumn(name = "report_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    private Set<TaskEntity> overdueTasks = new HashSet<>();

    /**
     * Конструктор инициализации
     */
    public ReportEntity() {
    }

    /**
     * Конструктор создания сущности
     * @param name название
     * @param startTime начало времени
     * @param endTime конец времени
     */
    public ReportEntity(String name,
                        LocalDateTime startTime,
                        LocalDateTime endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Получить название
     * @return название
     */
    public String getName() {
        return name;
    }

    /**
     * Установить название
     * @param name название
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Получить начало времени
     * @return начало времени
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Установить начало времени
     * @param startTime начало времени
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Получить конец времени
     * @return конец времени
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Установить конец времени
     * @param endTime конец времени
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
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
     * Получить выполненные задачи
     * @return выполненные задачи
     */
    public Set<TaskEntity> getCompletedTasks() {
        return completedTasks;
    }

    /**
     * Установить выполненные задачи
     * @param completedTasks выполненные задачи
     */
    public void setCompletedTasks(Set<TaskEntity> completedTasks) {
        this.completedTasks = completedTasks;
    }

    /**
     * Получить просроченные задачи
     * @return просроченные задачи
     */
    public Set<TaskEntity> getOverdueTasks() {
        return overdueTasks;
    }

    /**
     * Установить просроченные задачи
     * @param overdueTasks просроченные задачи
     */
    public void setOverdueTasks(Set<TaskEntity> overdueTasks) {
        this.overdueTasks = overdueTasks;
    }
}
