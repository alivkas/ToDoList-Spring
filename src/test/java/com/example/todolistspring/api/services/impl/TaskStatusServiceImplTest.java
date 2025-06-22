package com.example.todolistspring.api.services.impl;

import com.example.todolistspring.api.exceptions.NotOwnerException;
import com.example.todolistspring.api.exceptions.TaskNotFoundException;
import com.example.todolistspring.api.exceptions.UserNotFoundException;
import com.example.todolistspring.store.entities.TaskEntity;
import com.example.todolistspring.store.entities.UserEntity;
import com.example.todolistspring.store.enums.TaskStatus;
import com.example.todolistspring.store.repositories.TaskRepository;
import com.example.todolistspring.store.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit-тесты для EmailServiceImpl.
 * Проверяют корректную работу изменения статуса заданий.
 */
@ExtendWith(MockitoExtension.class)
class TaskStatusServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskStatusServiceImpl taskStatusService;

    /**
     * Проверяет, что метод updateTasksAfterDeadline
     * корректно обновляет статус задач с истёкшим дедлайном
     */
    @Test
    void updateTasksAfterDeadline_ShouldUpdateOverdueTasks() {
        LocalDateTime pastDeadline = LocalDateTime.now().minusHours(1);
        LocalDateTime futureDeadline = LocalDateTime.now().plusHours(1);

        TaskEntity overdueTask = new TaskEntity();
        overdueTask.setId(1L);
        overdueTask.setStatus(TaskStatus.OPENED);
        overdueTask.setDeadline(pastDeadline);

        TaskEntity notOverdueTask = new TaskEntity();
        notOverdueTask.setId(2L);
        notOverdueTask.setStatus(TaskStatus.OPENED);
        notOverdueTask.setDeadline(futureDeadline);

        when(taskRepository.findAllByStatus(TaskStatus.OPENED))
                .thenReturn(List.of(overdueTask, notOverdueTask));

        taskStatusService.updateTasksAfterDeadline();

        assertThat(overdueTask.getStatus()).isEqualTo(TaskStatus.OVERDUE);
        assertThat(notOverdueTask.getStatus()).isEqualTo(TaskStatus.OPENED);

        verify(taskRepository, times(1)).save(overdueTask);
        verify(taskRepository, never()).save(notOverdueTask);
    }

    /**
     * Проверяет успешное завершение задачи владельцем
     */
    @Test
    void finishTask_ShouldFinishTaskIfUserIsOwner() {
        Long taskId = 1L;
        String username = "user1";

        UserEntity user = new UserEntity();
        user.setUsername(username);

        TaskEntity task = new TaskEntity();
        task.setId(taskId);
        task.setStatus(TaskStatus.OPENED);
        task.setUser(user);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(taskRepository.findByIdAndStatus(taskId, TaskStatus.OPENED)).thenReturn(Optional.of(task));

        taskStatusService.finishTask(taskId, username);

        assertThat(task.getStatus()).isEqualTo(TaskStatus.FINISHED);
        verify(taskRepository, times(1)).save(task);
    }

    /**
     * Проверяет, что при попытке завершить чужую задачу выбрасывается NotOwnerException
     */
    @Test
    void finishTask_ShouldThrowNotOwnerException() {
        Long taskId = 1L;
        String username = "user1";
        String anotherUser = "user2";

        UserEntity user = new UserEntity();
        user.setUsername(username);

        UserEntity anotherUserEntity = new UserEntity();
        anotherUserEntity.setUsername(anotherUser);

        TaskEntity task = new TaskEntity();
        task.setId(taskId);
        task.setStatus(TaskStatus.OPENED);
        task.setUser(anotherUserEntity);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(taskRepository.findByIdAndStatus(taskId, TaskStatus.OPENED)).thenReturn(Optional.of(task));

        assertThatThrownBy(() -> taskStatusService.finishTask(taskId, username))
                .isInstanceOf(NotOwnerException.class)
                .hasMessageContaining(username);

        verify(taskRepository, never()).save(any());
    }

    /**
     * Проверяет, что при попытке завершить несуществующую задачу выбрасывается TaskNotFoundException
     */
    @Test
    void finishTask_ShouldThrowTaskNotFoundException() {
        Long taskId = 1L;
        String username = "user1";

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new UserEntity()));
        when(taskRepository.findByIdAndStatus(taskId, TaskStatus.OPENED)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskStatusService.finishTask(taskId, username))
                .isInstanceOf(TaskNotFoundException.class);
    }

    /**
     * Проверяет, что при попытке завершить задачу несуществующим пользователем выбрасывается UserNotFoundException
     */
    @Test
    void finishTask_ShouldThrowUserNotFoundException() {
        Long taskId = 1L;
        String username = "non_existing_user";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskStatusService.finishTask(taskId, username))
                .isInstanceOf(UserNotFoundException.class);
    }
}