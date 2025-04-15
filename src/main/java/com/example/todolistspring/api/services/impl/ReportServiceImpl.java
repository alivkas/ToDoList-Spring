package com.example.todolistspring.api.services.impl;

import com.example.todolistspring.api.dto.ReportDto;
import com.example.todolistspring.api.services.impl.interfaces.ReportService;
import com.example.todolistspring.mapper.ReportMapper;
import com.example.todolistspring.store.entities.ReportEntity;
import com.example.todolistspring.store.entities.TaskEntity;
import com.example.todolistspring.store.repositories.ReportRepository;
import com.example.todolistspring.store.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final TaskRepository taskRepository;
    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;

    public ReportServiceImpl(TaskRepository taskRepository, ReportRepository reportRepository, ReportMapper reportMapper) {
        this.taskRepository = taskRepository;
        this.reportRepository = reportRepository;
        this.reportMapper = reportMapper;
    }

    @Override
    public ReportDto generateReport(LocalDateTime startDate, LocalDateTime endDate) {
        List<TaskEntity> completedTasks = taskRepository.findCompletedTasksBetween(startDate, endDate);
        List<TaskEntity> overdueTasks = taskRepository.findOverdueTasksBetween(startDate, endDate);

        ReportEntity report = new ReportEntity("Отчет", startDate,endDate);
        report.setCompletedTasks(new HashSet<>(completedTasks));
        report.setOverdueTasks(new HashSet<>(overdueTasks));

        reportRepository.save(report);

        return reportMapper.toDto(report);
    }
}
