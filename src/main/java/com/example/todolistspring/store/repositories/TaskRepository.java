package com.example.todolistspring.store.repositories;

import com.example.todolistspring.store.entities.TagEntity;
import com.example.todolistspring.store.entities.TaskEntity;
import com.example.todolistspring.store.entities.UserEntity;
import com.example.todolistspring.store.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    @Query("SELECT t FROM TaskEntity t JOIN t.tags tag WHERE tag.name = :tagName AND t.user = :user")
    List<TaskEntity> findByTagsNameAndUser(@Param("tagName") String tagName,
                                           @Param("user") UserEntity user);


    /**
     * Найти список задач с указанным статусом
     *
     * @param status статус задачи (например, OPENED, FINISHED и т.д.)
     * @return список задач с заданным статусом
     */
    @Query("SELECT t FROM TaskEntity t WHERE t.status = :status AND t.user = :user")
    List<TaskEntity> findByStatusAndUser(@Param("status") String status,
                                  @Param("user") UserEntity user);

    /**
     * Найти список задач, созданных в заданном промежутке времени
     *
     * @param startDate начальная дата и время интервала (включительно)
     * @param endDate   конечная дата и время интервала (включительно)
     * @return список задач, созданных между {@code startDate} и {@code endDate}
     */
    @Query("SELECT t FROM TaskEntity t WHERE t.createdAt BETWEEN :startDate AND :endDate AND t.user = :user")
    List<TaskEntity> findByCreatedAtBetweenAndUser(@Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate,
                                            @Param("user") UserEntity user);

    /**
     * Найти список выполненных задач (со статусом FINISHED), созданных в заданном промежутке времени
     *
     * @param startDate начальная дата и время интервала (включительно)
     * @param endDate   конечная дата и время интервала (включительно)
     * @return список выполненных задач, созданных между {@code startDate} и {@code endDate}
     */
    @Query("SELECT t FROM TaskEntity t WHERE t.status = 'FINISHED' AND t.createdAt BETWEEN :startDate AND :endDate AND t.user = :user")
    List<TaskEntity> findCompletedTasksBetweenAndUser(@Param("startDate") LocalDateTime startDate,
                                               @Param("endDate") LocalDateTime endDate,
                                               @Param("user") UserEntity user);

    /**
     * Найти список просроченных задач (со статусом OVERDUE), созданных в заданном промежутке времени
     *
     * @param startDate начальная дата и время интервала (включительно)
     * @param endDate   конечная дата и время интервала (включительно)
     * @return список просроченных задач, созданных между {@code startDate} и {@code endDate}
     */
    @Query("SELECT t FROM TaskEntity t WHERE t.status = 'OVERDUE' AND t.createdAt BETWEEN :startDate AND :endDate AND t.user = :user")
    List<TaskEntity> findOverdueTasksBetweenAndUser(@Param("startDate") LocalDateTime startDate,
                                             @Param("endDate") LocalDateTime endDate,
                                             @Param("user") UserEntity user);

    /**
     * Получить все задачи пользователя
     * @param user пользователь
     * @return список задач пользователя
     */
    List<TaskEntity> findAllByUser(UserEntity user);

    /**
     * Получить все задания по их статусу
     * @param taskStatus статус задания
     * @return список заданий
     */
    List<TaskEntity> findAllByStatus(TaskStatus taskStatus);

    /**
     * Получить задание по ее id и статусу
     * @param taskId id задания
     * @param status статус задания
     * @return задание
     */
    Optional<TaskEntity> findByIdAndStatus(Long taskId, TaskStatus status);
}
