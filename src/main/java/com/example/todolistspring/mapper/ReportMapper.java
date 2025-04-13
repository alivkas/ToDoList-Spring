package com.example.todolistspring.mapper;

import com.example.todolistspring.api.dto.ReportDto;
import com.example.todolistspring.store.entities.ReportEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Меппер отчетов
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, TaskMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReportMapper {

    /**
     * Маппинг в сущность
     * @param dto дто
     * @return сущность
     */
    @Mapping(target = "user", source = "userDto")
    @Mapping(target = "completedTasks", source = "completedTasksDtos")
    @Mapping(target = "overdueTasks", source = "overdueTasksDtos")
    ReportEntity toEntity(ReportDto dto);

    /**
     * Маппинг в дто
     * @param entity сущность
     * @return дто
     */
    @Mapping(target = "userDto", source = "user")
    @Mapping(target = "completedTasksDtos", source = "completedTasks")
    @Mapping(target = "overdueTasksDtos", source = "overdueTasks")
    ReportDto toDto(ReportEntity entity);

    /**
     * Обновить сущность по дто
     * @param dto дто
     * @param entity сущность
     */
    @Mapping(target = "user", source = "userDto")
    @Mapping(target = "completedTasks", source = "completedTasksDtos")
    @Mapping(target = "overdueTasks", source = "overdueTasksDtos")
    void updateEntity(ReportDto dto, @MappingTarget ReportEntity entity);
}
