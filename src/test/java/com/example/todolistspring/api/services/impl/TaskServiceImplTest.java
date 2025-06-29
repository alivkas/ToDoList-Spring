package com.example.todolistspring.api.services.impl;

import com.example.todolistspring.api.dto.TaskDto;
import com.example.todolistspring.api.exceptions.NotOwnerException;
import com.example.todolistspring.api.exceptions.TaskNotFoundException;
import com.example.todolistspring.api.exceptions.UserNotFoundException;
import com.example.todolistspring.mapper.TaskMapper;
import com.example.todolistspring.store.entities.TaskEntity;
import com.example.todolistspring.store.entities.UserEntity;
import com.example.todolistspring.store.repositories.ReportRepository;
import com.example.todolistspring.store.repositories.TaskRepository;
import com.example.todolistspring.store.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Модульные тесты
 * Проверяют бизнес-логику операций управления задачами, включая:
 * - Создание задач
 * - Обновление задач
 * - Удаление задач
 * - Получение задач
 * - Фильтрацию задач
 */
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ReportRepository reportRepository;
    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private AutoCloseable closeable;

    private final String username = "user";
    private final Long userId = 1L;
    private final Long taskId = 100L;

    private UserEntity userEntity;
    private TaskEntity taskEntity;
    private TaskDto taskDto;

    /**
     * Подготавливает тестовое окружение перед каждым тестом
     * Инициализирует моки и тестовые данные
     */
    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setUsername(username);

        taskEntity = new TaskEntity();
        taskEntity.setId(taskId);
        taskEntity.setUser(userEntity);

        taskDto = mock(TaskDto.class);
        when(taskDto.title()).thenReturn("Test Task");
    }

    /**
     * Тест успешного создания задачи
     * Проверяет, что задача корректно создается и сохраняется для существующего пользователя
     */
    @Test
    void createTask_success() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(taskMapper.toEntity(taskDto)).thenReturn(taskEntity);
        when(taskRepository.save(taskEntity)).thenReturn(taskEntity);
        when(taskMapper.toDto(taskEntity)).thenReturn(taskDto);

        TaskDto result = taskService.createTask(taskDto, username);

        assertEquals(taskDto, result);
        verify(taskRepository).save(taskEntity);
    }

    /**
     * Тест создания задачи, когда пользователь не найден
     * Проверяет, что выбрасывается исключение UserNotFoundException
     */
    @Test
    void createTask_userNotFound() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> taskService.createTask(taskDto, username));
    }

    /**
     * Тест успешного обновления задачи
     * Проверяет, что задача корректно обновляется при выполнении всех условий
     */
    @Test
    void updateTask_success() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));
        when(taskMapper.toDto(any())).thenReturn(taskDto);

        TaskDto updatedDto = mock(TaskDto.class);
        when(updatedDto.title()).thenReturn("Updated Task");

        TaskDto result = taskService.updateTask(taskId, updatedDto, username);

        verify(taskMapper).updateEntity(updatedDto, taskEntity);
        verify(taskRepository).save(taskEntity);
        assertEquals(taskDto, result);
    }

    /**
     * Тест обновления задачи, когда пользователь не найден
     * Проверяет, что выбрасывается исключение UserNotFoundException
     */
    @Test
    void updateTask_userNotFound() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> taskService.updateTask(taskId, taskDto, username));
    }

    /**
     * Тест обновления задачи, когда задача не найдена
     * Проверяет, что выбрасывается исключение TaskNotFoundException
     */
    @Test
    void updateTask_taskNotFound() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(taskId, taskDto, username));
    }

    /**
     * Тест обновления задачи, когда пользователь не является владельцем
     * Проверяет, что выбрасывается исключение NotOwnerException
     */
    @Test
    void updateTask_notOwner() {
        UserEntity anotherUser = new UserEntity();
        anotherUser.setId(2L);
        anotherUser.setUsername("another");
        taskEntity.setUser(anotherUser);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));

        assertThrows(NotOwnerException.class, () -> taskService.updateTask(taskId, taskDto, username));
    }

    /**
     * Тест успешного удаления задачи
     * Проверяет, что задача корректно удаляется при выполнении всех условий
     */
    @Test
    void deleteTask_success() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));
        when(reportRepository.findByCompletedTasksContainsOrOverdueTasksContains(taskEntity, taskEntity))
                .thenReturn(Collections.emptyList());

        taskService.deleteTask(taskId, username);

        verify(taskRepository).delete(taskEntity);
    }

    /**
     * Тест удаления задачи, когда пользователь не найден
     * Проверяет, что выбрасывается исключение UserNotFoundException
     */
    @Test
    void deleteTask_userNotFound() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> taskService.deleteTask(taskId, username));
    }

    /**
     * Тест удаления задачи, когда задача не найдена
     * Проверяет, что выбрасывается исключение TaskNotFoundException
     */
    @Test
    void deleteTask_taskNotFound() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(taskId, username));
    }

    /**
     * Тест удаления задачи, когда пользователь не является владельцем
     * Проверяет, что выбрасывается исключение NotOwnerException.
     */
    @Test
    void deleteTask_notOwner() {
        UserEntity anotherUser = new UserEntity();
        anotherUser.setId(2L);
        anotherUser.setUsername("another");
        taskEntity.setUser(anotherUser);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));

        assertThrows(NotOwnerException.class, () -> taskService.deleteTask(taskId, username));
    }

    /**
     * Тест успешного получения задачи по ID
     * Проверяет, что задача корректно возвращается при выполнении всех условий
     */
    @Test
    void getTaskById_success() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));
        when(taskMapper.toDto(taskEntity)).thenReturn(taskDto);

        TaskDto result = taskService.getTaskById(taskId, username);

        assertEquals(taskDto, result);
    }

    /**
     * Тест получения задачи, когда пользователь не найден
     * Проверяет, что выбрасывается исключение UserNotFoundException
     */
    @Test
    void getTaskById_userNotFound() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> taskService.getTaskById(taskId, username));
    }

    /**
     * Тест получения задачи, когда задача не найдена
     * Проверяет, что выбрасывается исключение TaskNotFoundException
     */
    @Test
    void getTaskById_taskNotFound() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(taskId, username));
    }

    /**
     * Тест получения задачи, когда пользователь не является владельцем
     * Проверяет, что выбрасывается исключение NotOwnerException
     */
    @Test
    void getTaskById_notOwner() {
        UserEntity anotherUser = new UserEntity();
        anotherUser.setId(2L);
        anotherUser.setUsername("another");
        taskEntity.setUser(anotherUser);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));

        assertThrows(NotOwnerException.class, () -> taskService.getTaskById(taskId, username));
    }

    /**
     * Тест успешного получения всех задач пользователя
     * Проверяет, что возвращаются все задачи, принадлежащие пользователю
     */
    @Test
    void getAllTasks_success() {
        List<TaskEntity> taskEntities = List.of(taskEntity);
        List<TaskDto> taskDtos = List.of(taskDto);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(taskRepository.findAllByUser(userEntity)).thenReturn(taskEntities);
        when(taskMapper.toDto(taskEntity)).thenReturn(taskDto);

        List<TaskDto> result = taskService.getAllTasks(username);

        assertEquals(taskDtos, result);
    }

    /**
     * Тест получения всех задач, когда пользователь не найден
     * Проверяет, что выбрасывается исключение UserNotFoundException
     */
    @Test
    void getAllTasks_userNotFound() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> taskService.getAllTasks(username));
    }

    /**
     * Тест фильтрации задач по названию тега
     * Проверяет, что задачи корректно фильтруются по тегу
     */
    @Test
    void filterTasks_byTagName() {
        String tagName = "tag";
        List<TaskEntity> taskEntities = List.of(taskEntity);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(taskRepository.findByTagsNameAndUser(tagName, userEntity)).thenReturn(taskEntities);
        when(taskMapper.toDto(taskEntity)).thenReturn(taskDto);

        List<TaskDto> result = taskService.filterTasks(tagName, null, null, null, username);

        assertEquals(1, result.size());
        assertEquals(taskDto, result.get(0));
    }

    /**
     * Тест фильтрации задач по статусу
     * Проверяет, что задачи корректно фильтруются по статусу.
     */
    @Test
    void filterTasks_byStatus() {
        String status = "OPENED";
        List<TaskEntity> taskEntities = List.of(taskEntity);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(taskRepository.findByStatusAndUser(status, userEntity)).thenReturn(taskEntities);
        when(taskMapper.toDto(taskEntity)).thenReturn(taskDto);

        List<TaskDto> result = taskService.filterTasks(null, status, null, null, username);

        assertEquals(1, result.size());
        assertEquals(taskDto, result.get(0));
    }

    /**
     * Тест фильтрации задач по диапазону дат создания
     * Проверяет, что задачи корректно фильтруются по дате создания
     */
    @Test
    void filterTasks_byDate() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();
        List<TaskEntity> taskEntities = List.of(taskEntity);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(taskRepository.findByCreatedAtBetweenAndUser(start, end, userEntity)).thenReturn(taskEntities);
        when(taskMapper.toDto(taskEntity)).thenReturn(taskDto);

        List<TaskDto> result = taskService.filterTasks(null, null, start, end, username);

        assertEquals(1, result.size());
        assertEquals(taskDto, result.get(0));
    }

    /**
     * Тест фильтрации задач, когда пользователь не найден
     * Проверяет, что выбрасывается исключение UserNotFoundException
     */
    @Test
    void filterTasks_userNotFound() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> taskService.filterTasks(null, null, null, null, username));
    }
}
