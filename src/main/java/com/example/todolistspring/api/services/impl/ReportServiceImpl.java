package com.example.todolistspring.api.services.impl;

import com.example.todolistspring.api.dto.ReportDto;
import com.example.todolistspring.api.exceptions.UserNotFoundException;
import com.example.todolistspring.api.services.interfaces.ReportService;
import com.example.todolistspring.mapper.ReportMapper;
import com.example.todolistspring.store.entities.ReportEntity;
import com.example.todolistspring.store.entities.TaskEntity;
import com.example.todolistspring.store.entities.UserEntity;
import com.example.todolistspring.store.repositories.ReportRepository;
import com.example.todolistspring.store.repositories.TaskRepository;
import com.example.todolistspring.store.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ReportServiceImpl implements ReportService {

    private final TaskRepository taskRepository;
    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;
    private final UserRepository userRepository;

    public ReportServiceImpl(TaskRepository taskRepository,
                             ReportRepository reportRepository,
                             ReportMapper reportMapper,
                             UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.reportRepository = reportRepository;
        this.reportMapper = reportMapper;
        this.userRepository = userRepository;
    }

    @Async("reportTaskExecutor")
    @Transactional
    @Override
    public CompletableFuture<ReportDto> generateReport(LocalDateTime startDate,
                                                       LocalDateTime endDate,
                                                       String username) {
        UserEntity currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        List<TaskEntity> completedTasks = taskRepository.findCompletedTasksBetweenAndUser(startDate,
                endDate,
                currentUser);
        List<TaskEntity> overdueTasks = taskRepository.findOverdueTasksBetweenAndUser(startDate,
                endDate,
                currentUser);

        ReportEntity report = new ReportEntity("Отчет", startDate, endDate);
        report.setCompletedTasks(new HashSet<>(completedTasks));
        report.setOverdueTasks(new HashSet<>(overdueTasks));
        report.setUser(currentUser);

        reportRepository.save(report);

        ReportDto reportDto = reportMapper.toDto(report);
        return CompletableFuture.completedFuture(reportDto);
    }
}
