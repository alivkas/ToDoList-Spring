package com.example.todolistspring.store.repositories;

import com.example.todolistspring.store.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий для работы с сущностью
 * Предоставляет методы для поиска и фильтрации задач по различным критериям
 */
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    /**
     * Найти список задач, которые содержат тег с указанным именем
     *
     * @param tagName имя тега, по которому производится фильтрация
     * @return список задач, связанных с тегом с именем {@code tagName}
     */
    @Query("SELECT t FROM TaskEntity t JOIN t.tags tag WHERE tag.name = :tagName")
    List<TaskEntity> findByTags_Name(@Param("tagName") String tagName);

    /**
     * Найти список задач с указанным статусом
     *
     * @param status статус задачи (например, OPENED, FINISHED и т.д.)
     * @return список задач с заданным статусом
     */
    @Query("SELECT t FROM TaskEntity t WHERE t.status = :status")
    List<TaskEntity> findByStatus(@Param("status") String status);

    /**
     * Найти список задач, созданных в заданном промежутке времени
     *
     * @param startDate начальная дата и время интервала (включительно)
     * @param endDate   конечная дата и время интервала (включительно)
     * @return список задач, созданных между {@code startDate} и {@code endDate}
     */
    @Query("SELECT t FROM TaskEntity t WHERE t.createdAt BETWEEN :startDate AND :endDate")
    List<TaskEntity> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate);

    /**
     * Найти список выполненных задач (со статусом FINISHED), созданных в заданном промежутке времени
     *
     * @param startDate начальная дата и время интервала (включительно)
     * @param endDate   конечная дата и время интервала (включительно)
     * @return список выполненных задач, созданных между {@code startDate} и {@code endDate}
     */
    @Query("SELECT t FROM TaskEntity t WHERE t.status = 'FINISHED' AND t.createdAt BETWEEN :startDate AND :endDate")
    List<TaskEntity> findCompletedTasksBetween(@Param("startDate") LocalDateTime startDate,
                                               @Param("endDate") LocalDateTime endDate);

    /**
     * Найти список просроченных задач (со статусом OVERDUE), созданных в заданном промежутке времени
     *
     * @param startDate начальная дата и время интервала (включительно)
     * @param endDate   конечная дата и время интервала (включительно)
     * @return список просроченных задач, созданных между {@code startDate} и {@code endDate}
     */
    @Query("SELECT t FROM TaskEntity t WHERE t.status = 'OVERDUE' AND t.createdAt BETWEEN :startDate AND :endDate")
    List<TaskEntity> findOverdueTasksBetween(@Param("startDate") LocalDateTime startDate,
                                             @Param("endDate") LocalDateTime endDate);
}
