package com.example.todolistspring.mapper;

import com.example.todolistspring.api.dto.UserDto;
import com.example.todolistspring.store.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Маппер пользователей
 */
@Mapper(componentModel = "spring", uses = {TaskMapper.class, ReportMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    /**
     * Маппинг в сущность
     * @param dto дто
     * @param passwordEncoder кодировщик пароля
     * @return сущность
     */
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.password()))")
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "tasks", source = "dto.taskDtos")
    @Mapping(target = "reports", source = "dto.reportDtos")
    UserEntity toEntity(UserDto dto, PasswordEncoder passwordEncoder);

    /**
     * Маппинг в дто
     * @param entity сущность
     * @return дто
     */
    @Mapping(target = "password", constant = "")
    @Mapping(target = "passwordCheck", constant = "")
    @Mapping(target = "taskDtos", source = "tasks")
    @Mapping(target = "reportDtos", source = "reports")
    UserDto toDto(UserEntity entity);

    /**
     * Обновление сущности по дто
     * @param dto дто
     * @param entity сущность
     */
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "tasks", source = "dto.taskDtos")
    @Mapping(target = "reports", source = "dto.reportDtos")
    void updateEntity(UserDto dto, @MappingTarget UserEntity entity);
}
