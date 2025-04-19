package com.example.todolistspring.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * Дто проекта
 * @param name название
 * @param description описание
 * @param taskId id задания
 */
public record ProjectDto(@Size(max = 255, message = "Максимальная длина - 255 символов")
                         @NotBlank
                         @NotEmpty
                         String name,
                         @Size(max = 4000, message = "Максимальная длина - 4000 символов")
                         @NotBlank
                         @NotEmpty
                         String description,
                         Long taskId) {
}
