package com.example.todolistspring.api.services.impl;

import com.example.todolistspring.api.services.interfaces.NotificationService;
import com.example.todolistspring.store.entities.TaskEntity;
import com.example.todolistspring.store.repositories.TaskRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final TaskRepository taskRepository;
    private final EmailServiceImpl emailServiceImpl;

    public NotificationServiceImpl(TaskRepository taskRepository, EmailServiceImpl emailServiceImpl) {
        this.taskRepository = taskRepository;
        this.emailServiceImpl = emailServiceImpl;
    }

    @Async
    @Scheduled(cron = "${schedule.time.notify}")
    @Override
    public void sendDeadlineNotification() {
        List<TaskEntity> tasks = taskRepository.findAll();
        tasks.stream()
                .filter(task -> task.getDeadline() != null &&
                        LocalDateTime.now().isAfter(task.getDeadline().minusHours(1)))
                .forEach(task -> {
                    if (task.getUser() == null) {
                        return;
                    }
                    String email = task.getUser().getEmail();
                    if (email == null) {
                        return;
                    }
                    String subject = "Напоминание о дедлайне задачи";
                    String text = "Задача \"" + task.getTitle() + "\" скоро истекает!";
                    emailServiceImpl.sendSimpleMessage(email, subject, text);
                });
    }
}
