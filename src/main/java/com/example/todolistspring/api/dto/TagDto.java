package com.example.todolistspring.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * Дто тега
 * @param name имя тега
 */
public record TagDto(@Size(max = 255, message = "Максимальная длина - 255 символов")
                     @NotBlank
                     @NotEmpty
                     String name) {
}
