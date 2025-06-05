package com.example.todolistspring.api.services.impl;

import com.example.todolistspring.api.dto.TaskDto;
import com.example.todolistspring.api.exceptions.NotOwnerException;
import com.example.todolistspring.api.exceptions.TaskNotFoundException;
import com.example.todolistspring.api.exceptions.UserNotFoundException;
import com.example.todolistspring.api.services.interfaces.TaskService;
import com.example.todolistspring.mapper.TaskMapper;
import com.example.todolistspring.store.entities.TaskEntity;
import com.example.todolistspring.store.entities.UserEntity;
import com.example.todolistspring.store.repositories.TaskRepository;
import com.example.todolistspring.store.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса задач
 */
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    /**
     * Конструктор для внедрения TaskRepository, UserRepository, TaskMapper
     * @param taskRepository репозиторий задач
     * @param taskMapper маппер задач
     * @param userRepository репозиторий пользователя
     */
    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository,
                           TaskMapper taskMapper,
                           UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public TaskDto createTask(TaskDto taskDto, String username) {
        UserEntity currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        TaskEntity task = taskMapper.toEntity(taskDto);
        task.setUser(currentUser);

        return taskMapper.toDto(taskRepository.save(task));
    }

    @Transactional
    @Override
    public TaskDto updateTask(Long id, TaskDto taskDto, String username) {
        UserEntity currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(taskDto.title()));

        if (!task.getUser().equals(currentUser)) {
            throw new NotOwnerException(currentUser.getUsername(), task.getTitle());
        }

        taskMapper.updateEntity(taskDto, task);
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Transactional
    @Override
    public void deleteTask(Long id, String username) {
        UserEntity currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        if (!task.getUser().equals(currentUser)) {
            throw new NotOwnerException(currentUser.getUsername(), task.getTitle());
        }

        taskRepository.delete(task);
    }

    @Override
    public TaskDto getTaskById(Long id, String username) {
        UserEntity currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        if (!task.getUser().equals(currentUser)) {
            throw new NotOwnerException(currentUser.getUsername(), task.getTitle());
        }

        return taskMapper.toDto(task);
    }

    @Override
    public List<TaskDto> getAllTasks(String username) {
        UserEntity currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        List<TaskEntity> tasks = taskRepository.findAllByUser(currentUser);

        return tasks.stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> filterTasks(String tagName,
                                     String status,
                                     LocalDateTime startDate,
                                     LocalDateTime endDate,
                                     String username) {
        UserEntity currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        List<TaskEntity> tasks = new ArrayList<>();
        if (tagName != null) {
            tasks.addAll(taskRepository.findByTagsNameAndUser(tagName, currentUser));
        }
        if (status != null) {
            tasks.addAll(taskRepository.findByStatusAndUser(status, currentUser));
        }
        if (startDate != null && endDate != null) {
            tasks.addAll(taskRepository.findByCreatedAtBetweenAndUser(startDate, endDate, currentUser));
        }

        tasks = tasks.stream().distinct().collect(Collectors.toList());

        return tasks.stream().map(taskMapper::toDto).collect(Collectors.toList());

    }
}
