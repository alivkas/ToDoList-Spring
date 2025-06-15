package com.example.todolistspring.controllers;

import com.example.todolistspring.api.dto.TaskDto;
import com.example.todolistspring.api.services.interfaces.TaskService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@Validated
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping("/task-form")
    public String showTaskForm(@RequestParam(value = "id", required = false) Long taskId,
                               Model model,
                               Principal principal) {
        if (!model.containsAttribute("taskDto")) {
            if (taskId != null) {
                TaskDto taskDto = taskService.getTaskById(taskId, principal.getName());
                model.addAttribute("taskDto", taskDto);
                model.addAttribute("taskId", taskId);
            } else {
                model.addAttribute("taskDto", new TaskDto(null,
                        "", "", null,
                        null, null, null, null, null,
                        null, null
                ));
            }
        }
        return "task-form"; // resources/templates/task-form.html
    }

    @PostMapping("/task-form")
    public String submitTaskForm(@RequestParam(value = "id", required = false) Long taskId,
                                 @ModelAttribute("taskDto") @Valid TaskDto taskDto,
                                 BindingResult bindingResult,
                                 Principal principal,
                                 RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.taskDto", bindingResult);
            redirectAttributes.addFlashAttribute("taskDto", taskDto);
            if (taskId != null) {
                return "redirect:/task-form?id=" + taskId;
            }
            return "redirect:/task-form";
        }

        if (taskId != null) {
            taskService.updateTask(taskId, taskDto, principal.getName());
        } else {
            taskService.createTask(taskDto, principal.getName());
        }

        redirectAttributes.addFlashAttribute("message", "Задача успешно сохранена");
        return "redirect:/dashboard";
    }
}
