package com.example.todolistspring.api.services.interfaces;

/**
 * Интерфейс сервиса уведомлений
 *
 * Определяет метод для отправки уведомлений о приближении дедлайна задач
 */
public interface NotificationService {

    /**
     * Отправить уведомления о задачах, у которых дедлайн наступает в течение следующего часа
     *
     * Метод вызывается по расписанию
     */
    void sendDeadlineNotification();
}
