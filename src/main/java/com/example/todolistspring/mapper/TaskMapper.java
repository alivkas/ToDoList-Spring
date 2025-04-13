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
        uses = {UserMapper.class, TagMapper.class, CommentMapper.class, ProjectMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TaskMapper {

    /**
     * Мамппинг в сущность
     * @param dto дто
     * @return сущность
     */
    @Mapping(target = "user", source = "userDto")
    @Mapping(target = "tags", source = "tagDtos")
    @Mapping(target = "comment", source = "commentDto")
    @Mapping(target = "project", source = "projectDto")
    TaskEntity toEntity(TaskDto dto);

    /**
     * Маппинг в дто
     * @param entity сущность
     * @return дто
     */
    @Mapping(target = "userDto", source = "user")
    @Mapping(target = "tagDtos", source = "tags")
    @Mapping(target = "commentDto", source = "comment")
    @Mapping(target = "projectDto", source = "project")
    TaskDto toDto(TaskEntity entity);

    /**
     * Обновление сущности по дто
     * @param dto дто
     * @param entity сущность
     */
    @Mapping(target = "user", source = "userDto")
    @Mapping(target = "tags", source = "tagDtos")
    @Mapping(target = "comment", source = "commentDto")
    @Mapping(target = "project", source = "projectDto")
    void updateEntity(TaskDto dto, @MappingTarget TaskEntity entity);
}
