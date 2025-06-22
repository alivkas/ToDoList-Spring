package com.example.todolistspring.controllers;

import com.example.todolistspring.api.dto.TaskDto;
import com.example.todolistspring.api.services.interfaces.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Comparator;

@Controller
public class DashboardController {
    private final TaskService taskService;
    public DashboardController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Principal principal) {
        var tasks = taskService.getAllTasks(principal.getName());
        tasks.sort(Comparator.comparing(TaskDto::priority).reversed());
        model.addAttribute("tasks", tasks);
        return "dashboard"; // resources/templates/dashboard.html
    }
}
