package com.example.todolistspring.api.services.interfaces;

/**
 * Интерфейс сервиса для отправки email-сообщений
 */
public interface EmailService {

    /**
     * Отправить простое текстовое email-сообщение
     *
     * @param to      адрес получателя
     * @param subject тема письма
     * @param text    текст письма
     */
    void sendSimpleMessage(String to, String subject, String text);
}
