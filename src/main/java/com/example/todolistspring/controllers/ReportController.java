package com.example.todolistspring.controllers;

import com.example.todolistspring.api.dto.TaskDto;
import com.example.todolistspring.api.services.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ReportController {

    private final TaskServiceImpl taskService;

    @Autowired
    public ReportController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/report")
    public String showReportPage(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime endDate,
            Principal principal,
            Model model
    ) {
        List<TaskDto> tasks = taskService.getAllTasks(principal.getName());

        if (startDate != null && endDate != null) {
            tasks = tasks.stream()
                    .filter(task -> {
                        LocalDateTime due = task.deadline();
                        return due != null && !due.isBefore(startDate) && !due.isAfter(endDate);
                    })
                    .collect(Collectors.toList());
        }

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("tasks", tasks);

        return "report"; // report.html
    }
}

