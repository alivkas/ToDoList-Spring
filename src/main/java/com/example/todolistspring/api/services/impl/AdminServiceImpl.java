package com.example.todolistspring.api.services.impl;

import com.example.todolistspring.api.dto.TaskDto;
import com.example.todolistspring.api.dto.UserDto;
import com.example.todolistspring.api.exceptions.TaskNotFoundException;
import com.example.todolistspring.api.exceptions.UserNotFoundException;
import com.example.todolistspring.api.services.interfaces.AdminService;
import com.example.todolistspring.mapper.TaskMapper;
import com.example.todolistspring.mapper.UserMapper;
import com.example.todolistspring.store.entities.TaskEntity;
import com.example.todolistspring.store.entities.UserEntity;
import com.example.todolistspring.store.repositories.TaskRepository;
import com.example.todolistspring.store.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса админа
 */
@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final UserMapper userMapper;
    private final TaskMapper taskMapper;

    private final static Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);

    /**
     * Внедрение UserRepository, TaskRepository, UserMapper, TaskMapper
     * @param userRepository репозиторий пользователя
     * @param taskRepository репозиторий задания
     * @param userMapper маппер пользователя
     * @param taskMapper маппер задания
     */
    @Autowired
    public AdminServiceImpl(UserRepository userRepository,
                            TaskRepository taskRepository,
                            UserMapper userMapper,
                            TaskMapper taskMapper) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.userMapper = userMapper;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<TaskDto> getAllTasks() {
        List<TaskEntity> tasks = taskRepository.findAll();

        LOGGER.debug("Получен список всех заданий админом");

        return tasks.stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto getTaskById(Long taskId) {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));

        LOGGER.debug("Получено задание с id {} админом", taskId);

        return taskMapper.toDto(task);
    }

    @Transactional
    @Override
    public void deleteTaskById(Long taskId) {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));

        LOGGER.debug("Удалено задание с id {} админом", taskId);

        taskRepository.delete(task);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();

        LOGGER.debug("Получены все пользователи админом");

        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        LOGGER.debug("Получен пользователь {} админом", user.getEmail());

        return userMapper.toDto(user);
    }

    @Override
    public void deleteUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        LOGGER.debug("Удален пользователь {} админом", user.getEmail());

        userRepository.delete(user);
    }
}
