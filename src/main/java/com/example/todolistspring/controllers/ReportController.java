package com.example.todolistspring.controllers;

import com.example.todolistspring.api.dto.ReportDto;
import com.example.todolistspring.api.services.interfaces.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@Controller
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/report")
    public String showReportPage(
            @AuthenticationPrincipal UserDetails userDetails,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Model model) {

        if (startDate != null && endDate != null && userDetails != null) {
            endDate = endDate.withHour(23).withMinute(59).withSecond(59).withNano(999_999_999);

            try {
                ReportDto report = reportService
                        .generateReport(startDate, endDate, userDetails.getUsername())
                        .get(); // ждём результат
                model.addAttribute("report", report);
            } catch (InterruptedException | ExecutionException e) {
                model.addAttribute("error", "Не удалось сгенерировать отчёт: " + e.getMessage());
            }
        }

        return "report";
    }
}
