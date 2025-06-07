package com.example.todolistspring.api.services.interfaces;

import com.example.todolistspring.api.dto.ReportDto;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

/**
 * Интерфейс сервиса для генерации отчетов
 *
 * Определяет метод для создания отчета на основе выполненных и просроченных задач
 * за указанный период времени
 */
public interface ReportService {

    /**
     * Сгенерировать отчет за указанный период
     *
     * @param startDate начальная дата и время периода отчета (включительно)
     * @param endDate   конечная дата и время периода отчета (включительно)
     * @param username  имя текущего пользователя
     * @return DTO с данными отчета, включающего выполненные и просроченные задачи
     */
    CompletableFuture<ReportDto> generateReport(LocalDateTime startDate, LocalDateTime endDate, String username);
}
