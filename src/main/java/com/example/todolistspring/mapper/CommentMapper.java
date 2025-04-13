package com.example.todolistspring.mapper;

import com.example.todolistspring.api.dto.CommentDto;
import com.example.todolistspring.store.entities.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Маппер комментариев
 */
@Mapper(componentModel = "spring", uses = TaskMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommentMapper {

    /**
     * Маппинг в дто
     * @param entity сущность
     * @return дто
     */
    @Mapping(target = "taskDto", source = "task")
    CommentDto toDto(CommentEntity entity);

    /**
     * Маппинг в сущность
     * @param dto дто
     * @return сущность
     */
    @Mapping(target = "task", source = "taskDto")
    CommentEntity toEntity(CommentDto dto);

    /**
     * Обновление сущности по дто
     * @param dto дто
     * @param entity сущность
     */
    @Mapping(target = "task", source = "taskDto")
    void updateEntity(CommentDto dto, @MappingTarget CommentEntity entity);
}
