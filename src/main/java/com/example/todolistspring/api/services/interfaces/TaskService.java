package com.example.todolistspring.api.services.interfaces;

import com.example.todolistspring.api.dto.TaskDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс сервиса для управления задачами
 *
 * Определяет основные операции создания, обновления, удаления,
 * получения и фильтрации задач
 */
public interface TaskService {

    /**
     * Создать новую задачу для текущего пользователя
     *
     * @param taskDto DTO задачи для создания
     * @param username имя текущего пользователя
     * @return DTO созданной задачи
     */
    TaskDto createTask(TaskDto taskDto, String username);

    /**
     * Обновить существующую задачу по её идентификатору
     * для текущего пользователя
     *
     * @param id      идентификатор задачи для обновления
     * @param taskDto DTO с новыми данными задачи
     * @param username имя текущего пользователя
     * @return DTO обновлённой задачи
     */
    TaskDto updateTask(Long id, TaskDto taskDto, String username);

    /**
     * Удалить задачу по её идентификатору у текущего пользователя
     *
     * @param id идентификатор задачи для удаления
     * @param username имя текущего пользователя
     */
    void deleteTask(Long id, String username);

    /**
     * Получить задачу по её идентификатору у текущего пользователя
     *
     * @param id идентификатор задачи
     * @param username имя текущего пользователя
     * @return DTO задачи
     */
    TaskDto getTaskById(Long id, String username);

    /**
     * Получить список всех задач у текущего пользователя
     *
     * @param username имя текущего пользователя
     * @return список DTO всех задач
     */
    List<TaskDto> getAllTasks(String username);

    /**
     * Отфильтровать задачи по тегу, статусу и/или диапазону дат создания
     * Если параметр равен null, фильтрация по нему не производится
     *
     * @param tagName   имя тега для фильтрации (может быть {@code null})
     * @param status    статус задачи для фильтрации (может быть {@code null})
     * @param startDate начало диапазона даты создания (может быть {@code null})
     * @param endDate   конец диапазона даты создания (может быть {@code null})
     * @param username имя текущего пользователя
     * @return список DTO задач, удовлетворяющих заданным критериям
     */
    List<TaskDto> filterTasks(String tagName,
                              String status,
                              LocalDateTime startDate,
                              LocalDateTime endDate,
                              String username);
}
