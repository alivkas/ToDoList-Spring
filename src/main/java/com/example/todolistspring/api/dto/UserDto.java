package com.example.todolistspring.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Дто пользователя
 * @param username имя пользователя
 * @param password пароль
 * @param passwordCheck повторный пароль
 * @param email почта
 * @param taskDtos список дто задач
 * @param reportDtos список дто отчетов
 */
public record UserDto(@Size(max = 25, message = "Максимальная длина - 25 символов")
                      @NotBlank
                      @NotEmpty
                      String username,
                      @Size(max = 100, message = "Максимальная длина - 100 символов")
                      @NotBlank
                      @NotEmpty
                      String password,
                      @Size(max = 100, message = "Максимальная длина - 100 символов")
                      @NotBlank
                      @NotEmpty
                      String passwordCheck,
                      @NotBlank
                      @NotEmpty
                      String email,
                      List<TaskDto> taskDtos,
                      List<ReportDto> reportDtos) {
}
