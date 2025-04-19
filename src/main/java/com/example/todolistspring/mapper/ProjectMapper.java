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
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProjectMapper {

    /**
     * Маппинг в сущность
     * @param dto дто
     * @return сущность
     */
    ProjectEntity toEntity(ProjectDto dto);

    /**
     * Маппинг в дто
     * @param entity сущность
     * @return дто
     */
    @Mapping(target = "taskId", source = "task.id")
    ProjectDto toDto(ProjectEntity entity);

    /**
     * Обновить сущность по дто
     * @param dto дто
     * @param entity сущность
     */
    void updateEntity(ProjectDto dto, @MappingTarget ProjectEntity entity);
}
