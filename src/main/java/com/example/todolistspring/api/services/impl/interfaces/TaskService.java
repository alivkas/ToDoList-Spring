package com.example.todolistspring.api.services.impl.interfaces;

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
     * Создать новую задачу
     *
     * @param taskDto DTO задачи для создания
     * @return DTO созданной задачи
     */
    TaskDto createTask(TaskDto taskDto);

    /**
     * Обновить существующую задачу по её идентификатору
     *
     * @param id      идентификатор задачи для обновления
     * @param taskDto DTO с новыми данными задачи
     * @return DTO обновлённой задачи
     */
    TaskDto updateTask(Long id, TaskDto taskDto);

    /**
     * Удалить задачу по её идентификатору
     *
     * @param id идентификатор задачи для удаления
     */
    void deleteTask(Long id);

    /**
     * Получить задачу по её идентификатору
     *
     * @param id идентификатор задачи
     * @return DTO задачи
     */
    TaskDto getTaskById(Long id);

    /**
     * Получить список всех задач
     *
     * @return список DTO всех задач
     */
    List<TaskDto> getAllTasks();

    /**
     * Отфильтровать задачи по тегу, статусу и/или диапазону дат создания
     * Если параметр равен null, фильтрация по нему не производится
     *
     * @param tagName   имя тега для фильтрации (может быть {@code null})
     * @param status    статус задачи для фильтрации (может быть {@code null})
     * @param startDate начало диапазона даты создания (может быть {@code null})
     * @param endDate   конец диапазона даты создания (может быть {@code null})
     * @return список DTO задач, удовлетворяющих заданным критериям
     */
    List<TaskDto> filterTasks(String tagName, String status, LocalDateTime startDate, LocalDateTime endDate);
}
