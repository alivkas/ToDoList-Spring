package com.example.todolistspring.mapper;

import com.example.todolistspring.api.dto.ProjectDto;
import com.example.todolistspring.store.entities.ProjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Маппер проекта
 */
@Mapper(componentModel = "spring", uses = TaskMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProjectMapper {

    /**
     * Маппинг в сущность
     * @param dto дто
     * @return сущность
     */
    @Mapping(target = "task", source = "taskDto")
    ProjectEntity toEntity(ProjectDto dto);

    /**
     * Маппинг в дто
     * @param entity сущность
     * @return дто
     */
    @Mapping(target = "taskDto", source = "task")
    ProjectDto toDto(ProjectEntity entity);

    /**
     * Обновить сущность по дто
     * @param dto дто
     * @param entity сущность
     */
    @Mapping(target = "task", source = "taskDto")
    void updateEntity(ProjectDto dto, @MappingTarget ProjectEntity entity);
}
