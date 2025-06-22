package com.example.todolistspring.api.services.interfaces;

/**
 * Сервис статуса задания
 */
public interface TaskStatusService {

    /**
     * Изменить статус задач, после дедлайна
     */
    void updateTasksAfterDeadline();

    /**
     * Завершить задачу по ее id, текущим пользователем
     * @param taskId id задачи
     * @param username имя пользователя
     */
    void finishTask(Long taskId, String username);
}
