package com.example.todolistspring.api.services.interfaces;

import com.example.todolistspring.api.dto.TaskDto;
import com.example.todolistspring.api.dto.UserDto;

import java.util.List;

/**
 * Сервис для админа
 */
public interface AdminService {

    /**
     * Получить все задания
     * @return список заданий
     */
    List<TaskDto> getAllTasks();

    /**
     * Получить задание по id
     * @param taskId id задания
     * @return дто задания
     */
    TaskDto getTaskById(Long taskId);

    /**
     * Удалить задание по его id
     * @param taskId if задания
     */
    void deleteTaskById(Long taskId);

    /**
     * Получить всех пользователей
     * @return список пользователей
     */
    List<UserDto> getAllUsers();

    /**
     * Получить пользователя по его юзернейму
     * @param username юзернейм
     * @return дто пользователя
     */
    UserDto getUserByUsername(String username);

    /**
     * Удалить пользователя по юзернейму
     * @param username юзернейм
     */
    void deleteUserByUsername(String username);
}
