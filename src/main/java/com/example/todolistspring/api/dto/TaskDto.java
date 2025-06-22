package com.example.todolistspring.api.dto;

import com.example.todolistspring.store.enums.TaskPriority;
import com.example.todolistspring.store.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Дто задания
 *
 * @param title       название
 * @param description описание
 * @param createdAt   время создания
 * @param status      статус
 * @param priority    приоритет
 * @param userId      id пользователя
 * @param tagDtos     множество дто тегов
 * @param commentId   id комментариев
 * @param projectId   id проекта
 */
public record TaskDto(Long id,
                      @Size(max = 255, message = "Максимальная длина - 255 символов")
                      @NotBlank
                      @NotEmpty
                      String title,
                      @Size(max = 4000, message = "Максимальная длина - 4000 символов")
                      @NotBlank
                      @NotEmpty
                      String description,
                      LocalDateTime createdAt,
                      LocalDateTime deadline,
                      TaskStatus status,
                      TaskPriority priority,
                      Long userId,
                      Set<TagDto> tagDtos,
                      Long commentId,
                      Long projectId) {

}
