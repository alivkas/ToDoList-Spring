package com.example.todolistspring.mapper;

import com.example.todolistspring.api.dto.TaskDto;
import com.example.todolistspring.store.entities.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Маппер заданий
 */
@Mapper(componentModel = "spring",
        uses = {TagMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TaskMapper {

    /**
     * Мамппинг в сущность
     * @param dto дто
     * @return сущность
     */
    @Mapping(target = "tags", source = "tagDtos")
    TaskEntity toEntity(TaskDto dto);

    /**
     * Маппинг в дто
     * @param entity сущность
     * @return дто
     */
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "tagDtos", source = "tags")
    @Mapping(target = "commentId", source = "comment.id")
    @Mapping(target = "projectId", source = "project.id")
    TaskDto toDto(TaskEntity entity);

    /**
     * Обновление сущности по дто
     * @param dto дто
     * @param entity сущность
     */
    @Mapping(target = "tags", source = "tagDtos")
    void updateEntity(TaskDto dto, @MappingTarget TaskEntity entity);
}
