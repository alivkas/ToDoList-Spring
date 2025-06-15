package com.example.todolistspring.api.configuration;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Конфигурационный класс,
 * включающий поддержку планировщика задач Spring для выполнения методов с аннотацией @Scheduled
 */
@Configuration
@EnableScheduling
public class SchedulingConfig {
}
