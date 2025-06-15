package com.example.todolistspring.api.services.impl;

import com.example.todolistspring.store.entities.TaskEntity;
import com.example.todolistspring.store.entities.UserEntity;
import com.example.todolistspring.store.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

/**
 * Unit-тесты для NotificationServiceImpl
 * Проверяют корректную работу метода отправки уведомлений о дедлайнах
 */
class NotificationServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private EmailServiceImpl emailServiceImpl;

    private NotificationServiceImpl notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        notificationService = new NotificationServiceImpl(taskRepository, emailServiceImpl);
    }

    /**
     * Проверяет, что уведомления отправляются только для задач с дедлайном менее чем через час
     */
    @Test
    void sendDeadlineNotification_shouldSendEmailsForTasksWithDeadlineInLessThanHour() {
        UserEntity user = new UserEntity();
        user.setEmail("user@example.com");

        TaskEntity task1 = new TaskEntity();
        task1.setUser(user);
        task1.setTitle("Task 1");
        task1.setDeadline(LocalDateTime.now().plusMinutes(30)); // дедлайн через 30 минут

        TaskEntity task2 = new TaskEntity();
        task2.setUser(user);
        task2.setTitle("Task 2");
        task2.setDeadline(LocalDateTime.now().plusHours(2)); // дедлайн через 2 часа

        TaskEntity task3 = new TaskEntity();
        task3.setUser(user);
        task3.setTitle("Task 3");
        task3.setDeadline(LocalDateTime.now().minusMinutes(10)); // дедлайн уже прошёл

        when(taskRepository.findAll()).thenReturn(List.of(task1, task2, task3));

        notificationService.sendDeadlineNotification();


        // Должно быть отправлено письмо только по task1 и task3 (у task2 дедлайн слишком далеко)
        verify(emailServiceImpl, times(1)).sendSimpleMessage(
                eq("user@example.com"),
                eq("Напоминание о дедлайне задачи"),
                contains("Task 1")
        );
        verify(emailServiceImpl, times(1)).sendSimpleMessage(
                eq("user@example.com"),
                eq("Напоминание о дедлайне задачи"),
                contains("Task 3")
        );
        verify(emailServiceImpl, times(0)).sendSimpleMessage(
                eq("user@example.com"),
                eq("Напоминание о дедлайне задачи"),
                contains("Task 2")
        );
    }

    /**
     * Проверяет, что метод корректно обрабатывает задачи без дедлайна (не отправляет письма)
     */
    @Test
    void sendDeadlineNotification_shouldSkipTasksWithoutDeadline() {
        UserEntity user = new UserEntity();
        user.setEmail("user@example.com");

        TaskEntity task = new TaskEntity();
        task.setUser(user);
        task.setTitle("No deadline task");
        task.setDeadline(null);

        when(taskRepository.findAll()).thenReturn(List.of(task));

        notificationService.sendDeadlineNotification();

        verify(emailServiceImpl, never()).sendSimpleMessage(anyString(), anyString(), anyString());
    }

    /**
     * Проверяет, что метод не вызывает emailServiceImpl, если список задач пуст
     */
    @Test
    void sendDeadlineNotification_shouldDoNothingIfNoTasks() {
        when(taskRepository.findAll()).thenReturn(List.of());

        notificationService.sendDeadlineNotification();

        verifyNoInteractions(emailServiceImpl);
    }

    /**
     * Проверяет, что метод не выбрасывает исключений, если у задачи нет пользователя
     */
    @Test
    void sendDeadlineNotification_shouldNotThrowIfTaskHasNoUser() {
        TaskEntity task = new TaskEntity();
        task.setUser(null);
        task.setTitle("No user");
        task.setDeadline(LocalDateTime.now().plusMinutes(10));

        when(taskRepository.findAll()).thenReturn(List.of(task));

        assertDoesNotThrow(() -> notificationService.sendDeadlineNotification());
        verify(emailServiceImpl, never()).sendSimpleMessage(anyString(), anyString(), anyString());
    }

    /**
     * Проверяет, что метод не выбрасывает исключений, если у пользователя нет email
     */
    @Test
    void sendDeadlineNotification_shouldNotThrowIfUserHasNoEmail() {
        UserEntity user = new UserEntity();
        user.setEmail(null);

        TaskEntity task = new TaskEntity();
        task.setUser(user);
        task.setTitle("No email");
        task.setDeadline(LocalDateTime.now().plusMinutes(10));

        when(taskRepository.findAll()).thenReturn(List.of(task));

        assertDoesNotThrow(() -> notificationService.sendDeadlineNotification());
        verify(emailServiceImpl, never()).sendSimpleMessage(anyString(), anyString(), anyString());
    }
}
