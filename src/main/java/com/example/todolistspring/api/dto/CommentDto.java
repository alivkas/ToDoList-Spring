package com.example.todolistspring.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * Дто комментария
 * @param text текст
 * @param createdAt дата создания
 * @param taskId id задания
 */
public record CommentDto(@Size(max = 1000, message = "максимальная длина - 1000 символов")
                         @NotBlank
                         @NotEmpty
                         String text,
                         LocalDateTime createdAt,
                         Long taskId) {
}
