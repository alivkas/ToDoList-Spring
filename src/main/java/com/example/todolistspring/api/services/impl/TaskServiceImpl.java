package com.example.todolistspring.api.services.impl;

import com.example.todolistspring.api.dto.TaskDto;
import com.example.todolistspring.api.services.impl.interfaces.TaskService;
import com.example.todolistspring.mapper.TaskMapper;
import com.example.todolistspring.store.entities.TaskEntity;
import com.example.todolistspring.store.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        TaskEntity entity = taskMapper.toEntity(taskDto);
        return taskMapper.toDto(taskRepository.save(entity));
    }

    @Override
    public TaskDto updateTask(Long id, TaskDto taskDto) {
        TaskEntity entity = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Задача не найдена"));
        taskMapper.updateEntity(taskDto, entity);
        return taskMapper.toDto(taskRepository.save(entity));
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new IllegalArgumentException("Задача не найдена");
        }
        taskRepository.deleteById(id);
    }

    @Override
    public TaskDto getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(taskMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Задача не найдена"));
    }

    @Override
    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> filterTasks(String tagName, String status,
                                     LocalDateTime startDate, LocalDateTime endDate) {
        List<TaskEntity> tasks = new ArrayList<>();
        if (tagName != null) tasks.addAll(taskRepository.findByTags_Name(tagName));
        if (status != null) tasks.addAll(taskRepository.findByStatus(status));
        if (startDate != null && endDate != null) tasks.addAll(
                taskRepository.findByCreatedAtBetween(startDate, endDate));

        tasks = tasks.stream().distinct().collect(Collectors.toList());

        return tasks.stream().map(taskMapper::toDto).collect(Collectors.toList());

    }
}
