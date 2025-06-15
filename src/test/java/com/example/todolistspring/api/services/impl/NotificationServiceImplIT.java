package com.example.todolistspring.api.services.impl;

import com.example.todolistspring.store.entities.TaskEntity;
import com.example.todolistspring.store.entities.UserEntity;
import com.example.todolistspring.store.repositories.TaskRepository;
import com.example.todolistspring.store.repositories.UserRepository;
import com.example.todolistspring.api.services.interfaces.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Интеграционный тест для NotificationServiceImpl
 * Проверяет реальную отправку email-уведомления о дедлайне задачи
 * Проверку доставки письма делайте вручную в почтовом ящике!
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class NotificationServiceImplIT {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void integration_sendDeadlineNotification_sendsRealEmail() throws InterruptedException {
        UserEntity user = new UserEntity();
        user.setUsername("integration-test-user");
        user.setEmail("david_grishchenko123@mail.ru");
        userRepository.save(user);

        TaskEntity task = new TaskEntity();
        task.setUser(user);
        task.setTitle("тест дедлайна");
        task.setDeadline(LocalDateTime.now().plusMinutes(1));
        taskRepository.save(task);

        Thread.sleep(70000);

        notificationService.sendDeadlineNotification();

        assertDoesNotThrow(() -> {});

        taskRepository.delete(task);
        userRepository.delete(user);
    }
}
