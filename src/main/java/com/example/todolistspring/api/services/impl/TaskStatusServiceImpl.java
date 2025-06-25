package com.example.todolistspring.api.services.impl;

import com.example.todolistspring.api.exceptions.NotOwnerException;
import com.example.todolistspring.api.exceptions.TaskNotFoundException;
import com.example.todolistspring.api.exceptions.UserNotFoundException;
import com.example.todolistspring.api.services.interfaces.TaskStatusService;
import com.example.todolistspring.store.entities.TaskEntity;
import com.example.todolistspring.store.entities.UserEntity;
import com.example.todolistspring.store.enums.TaskStatus;
import com.example.todolistspring.store.repositories.TaskRepository;
import com.example.todolistspring.store.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Реализация сервиса статуса задания
 */
@Service
public class TaskStatusServiceImpl implements TaskStatusService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private final static Logger LOGGER = LoggerFactory.getLogger(TaskStatusServiceImpl.class);

    /**
     * Внедрение TaskRepository, UserRepository
     * @param taskRepository репозиторий задания
     * @param userRepository репозиторий пользователя
     */
    @Autowired
    public TaskStatusServiceImpl(TaskRepository taskRepository,
                                 UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Scheduled(cron = "${schedule.time.update}")
    @Override
    public void updateTasksAfterDeadline() {
        List<TaskEntity> openedTasks = taskRepository.findAllByStatus(TaskStatus.OPENED);

        for (TaskEntity openedTask : openedTasks) {
            if (shouldUpdate(openedTask.getDeadline())) {
                openedTask.setStatus(TaskStatus.OVERDUE);
                taskRepository.save(openedTask);

                LOGGER.info("Статус задания {} изменен на {}",
                        openedTask.getId(),
                        openedTask.getStatus().name());
            }
        }
    }

    @Transactional
    @Override
    public void finishTask(Long taskId, String username) {
        UserEntity currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        TaskEntity openedTask = taskRepository.findByIdAndStatus(taskId, TaskStatus.OPENED)
                .orElseThrow(() -> new TaskNotFoundException(taskId));

        if (!openedTask.getUser().equals(currentUser)) {
            throw new NotOwnerException(username, openedTask.getTitle());
        }
        openedTask.setStatus(TaskStatus.FINISHED);
        taskRepository.save(openedTask);

        LOGGER.info("Задание {} завершено", openedTask.getId());
    }

    /**
     * Узнать, должно ли произойти обновление статуса
     * @param dateTime время задания
     * @return true - должно обновиться, false - не должно
     */
    private boolean shouldUpdate(LocalDateTime dateTime) {
        return LocalDateTime.now().isAfter(dateTime);
    }
}
