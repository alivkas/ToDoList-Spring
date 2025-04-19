package com.example.todolistspring.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Дто отчета
 * @param name название
 * @param startTime начало времени
 * @param endTime конец времени
 * @param userId id пользователя
 * @param completedTasksDtos множество дто выполненных задач
 * @param overdueTasksDtos множество дто просроченных задач
 */
public record ReportDto(@Size(max = 255, message = "Максимальная длина - 255 символов")
                        @NotBlank
                        @NotEmpty
                        String name,
                        LocalDateTime startTime,
                        LocalDateTime endTime,
                        Long userId,
                        Set<TaskDto> completedTasksDtos,
                        Set<TaskDto> overdueTasksDtos) {
}
