package com.example.todolistspring.controllers;

import com.example.todolistspring.api.dto.TaskDto;
import com.example.todolistspring.api.services.interfaces.TaskService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ReportController {

    private final TaskService taskService;

    public ReportController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/report")
    public String showReportPage(
            @AuthenticationPrincipal UserDetails userDetails,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Model model) {

        if (startDate != null && endDate != null && userDetails != null) {
            // Увеличиваем конец периода до конца дня
            endDate = endDate.withHour(23).withMinute(59).withSecond(59).withNano(999_999_999);

            List<TaskDto> tasks = taskService.filterTasks(null, null, startDate, endDate, userDetails.getUsername());
            model.addAttribute("tasks", tasks);
        }

        return "report";
    }
}

