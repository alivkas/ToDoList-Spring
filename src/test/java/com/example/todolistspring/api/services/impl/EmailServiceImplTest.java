package com.example.todolistspring.api.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

/**
 * Unit-тесты для EmailServiceImpl.
 * Проверяют корректную работу метода отправки писем.
 */
class EmailServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    private EmailServiceImpl emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        emailService = new EmailServiceImpl(mailSender);
    }

    /**
     * Проверяет, что метод sendSimpleMessage формирует и отправляет письмо с правильными параметрами
     */
    @Test
    void sendSimpleMessage_shouldSendMailWithCorrectParameters() {
        String to = "test@example.com";
        String subject = "Subject";
        String text = "Message body";

        emailService.sendSimpleMessage(to, subject, text);

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(captor.capture());

        SimpleMailMessage sentMessage = captor.getValue();
        assertArrayEquals(new String[]{to}, sentMessage.getTo());
        assertEquals(subject, sentMessage.getSubject());
        assertEquals(text, sentMessage.getText());
    }

    /**
     * Проверяет, что метод sendSimpleMessage не выбрасывает исключения при отправке письма
     */
    @Test
    void sendSimpleMessage_shouldNotThrowException() {
        String to = "recipient@example.com";
        String subject = "Test";
        String text = "Body";

        assertDoesNotThrow(() -> emailService.sendSimpleMessage(to, subject, text));
    }
}
