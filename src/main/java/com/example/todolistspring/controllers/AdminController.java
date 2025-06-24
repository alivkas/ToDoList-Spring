package com.example.todolistspring.controllers;

import com.example.todolistspring.api.dto.TaskDto;
import com.example.todolistspring.api.dto.UserDto;
import com.example.todolistspring.api.services.interfaces.AdminService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Контроллер для админских страниц
 */
@Controller
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Страница всех пользователей
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users")
    public String getAllUsers(Model model) {
        List<UserDto> users = adminService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/users"; // шаблон admin/users.html
    }

    /**
     * Страница всех задач
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/tasks")
    public String getAllTasks(Model model) {
        List<TaskDto> tasks = adminService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "admin/tasks"; // шаблон admin/tasks.html
    }

    @PostMapping("/admin/delete-user")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@RequestParam String username) {
        adminService.deleteUserByUsername(username);
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/delete-task")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteTask(@RequestParam Long taskId) {
        adminService.deleteTaskById(taskId);
        return "redirect:/admin/tasks";
    }
}
