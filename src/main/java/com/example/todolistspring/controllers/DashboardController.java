package com.example.todolistspring.controllers;

import com.example.todolistspring.api.dto.TaskDto;
import com.example.todolistspring.api.services.interfaces.TaskService;
import com.example.todolistspring.api.services.interfaces.TaskStatusService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Comparator;

@Controller
public class DashboardController {

    private final TaskService taskService;
    private final TaskStatusService taskStatusService;

    public DashboardController(TaskService taskService,
                               TaskStatusService taskStatusService) {
        this.taskService = taskService;
        this.taskStatusService = taskStatusService;
    }

    @GetMapping("/dashboard")
    @Operation(summary = "Главная страница")
    public String showDashboard(Model model, Principal principal) {
        var tasks = taskService.getAllTasks(principal.getName());
        tasks.sort(Comparator.comparing(TaskDto::priority).reversed());
        model.addAttribute("tasks", tasks);
        return "dashboard";
    }

    @PostMapping("/complete-task")
    public String completeTask(@RequestParam Long id, Principal principal) {
        taskStatusService.finishTask(id, principal.getName());
        return "redirect:/dashboard";
    }
}
