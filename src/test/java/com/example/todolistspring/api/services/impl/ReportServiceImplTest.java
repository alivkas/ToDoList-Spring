package com.example.todolistspring.api.services.impl;

import com.example.todolistspring.api.dto.ReportDto;
import com.example.todolistspring.api.exceptions.UserNotFoundException;
import com.example.todolistspring.mapper.ReportMapper;
import com.example.todolistspring.store.entities.ReportEntity;
import com.example.todolistspring.store.entities.TaskEntity;
import com.example.todolistspring.store.entities.UserEntity;
import com.example.todolistspring.store.repositories.ReportRepository;
import com.example.todolistspring.store.repositories.TaskRepository;
import com.example.todolistspring.store.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit-тесты для ReportServiceImpl
 * Проверяют корректную работу метода генерации отчёта
 */
class ReportServiceImplTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ReportRepository reportRepository;
    @Mock
    private ReportMapper reportMapper;
    @Mock
    private UserRepository userRepository;

    private ReportServiceImpl reportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reportService = new ReportServiceImpl(taskRepository, reportRepository, reportMapper, userRepository);
    }

    /**
     * Проверяет успешную генерацию отчёта для существующего пользователя
     * Ожидается, что отчёт будет сохранён и вернётся корректный ReportDto
     */
    @Test
    void generateReport_success() {
        String username = "user";
        LocalDateTime start = LocalDateTime.now().minusDays(7);
        LocalDateTime end = LocalDateTime.now();

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername(username);

        TaskEntity completedTask = new TaskEntity();
        completedTask.setId(10L);
        TaskEntity overdueTask = new TaskEntity();
        overdueTask.setId(20L);

        List<TaskEntity> completedTasks = List.of(completedTask);
        List<TaskEntity> overdueTasks = List.of(overdueTask);

        ReportEntity reportEntity = new ReportEntity("Отчет", start, end);
        reportEntity.setUser(user);
        reportEntity.setCompletedTasks(Set.copyOf(completedTasks));
        reportEntity.setOverdueTasks(Set.copyOf(overdueTasks));

        ReportDto reportDto = mock(ReportDto.class);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(taskRepository.findCompletedTasksBetweenAndUser(start, end, user)).thenReturn(completedTasks);
        when(taskRepository.findOverdueTasksBetweenAndUser(start, end, user)).thenReturn(overdueTasks);
        when(reportRepository.save(any(ReportEntity.class))).thenReturn(reportEntity);
        when(reportMapper.toDto(any(ReportEntity.class))).thenReturn(reportDto);

        CompletableFuture<ReportDto> future = reportService.generateReport(start, end, username);

        assertTrue(future.isDone());
        assertEquals(reportDto, future.join());
        verify(reportRepository).save(any(ReportEntity.class));
        verify(reportMapper).toDto(any(ReportEntity.class));
    }

    /**
     * Проверяет выброс UserNotFoundException при генерации отчёта для несуществующего пользователя
     */
    @Test
    void generateReport_userNotFound() {
        String username = "unknown";
        LocalDateTime start = LocalDateTime.now().minusDays(7);
        LocalDateTime end = LocalDateTime.now();

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> reportService.generateReport(start, end, username));
    }

    /**
     * Проверяет, что метод корректно работает, если нет выполненных и просроченных задач (пустые списки)
     */
    @Test
    void generateReport_noTasks() {
        String username = "user";
        LocalDateTime start = LocalDateTime.now().minusDays(7);
        LocalDateTime end = LocalDateTime.now();

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername(username);

        ReportEntity reportEntity = new ReportEntity("Отчет", start, end);
        reportEntity.setUser(user);

        ReportDto reportDto = mock(ReportDto.class);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(taskRepository.findCompletedTasksBetweenAndUser(start, end, user)).thenReturn(List.of());
        when(taskRepository.findOverdueTasksBetweenAndUser(start, end, user)).thenReturn(List.of());
        when(reportRepository.save(any(ReportEntity.class))).thenReturn(reportEntity);
        when(reportMapper.toDto(any(ReportEntity.class))).thenReturn(reportDto);

        CompletableFuture<ReportDto> future = reportService.generateReport(start, end, username);

        assertTrue(future.isDone());
        assertEquals(reportDto, future.join());
        verify(reportRepository).save(any(ReportEntity.class));
        verify(reportMapper).toDto(any(ReportEntity.class));
    }
}
