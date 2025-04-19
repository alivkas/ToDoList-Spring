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
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommentMapper {

    /**
     * Маппинг в дто
     * @param entity сущность
     * @return дто
     */
    @Mapping(target = "taskId", source = "task.id")
    CommentDto toDto(CommentEntity entity);

    /**
     * Маппинг в сущность
     * @param dto дто
     * @return сущность
     */
    CommentEntity toEntity(CommentDto dto);

    /**
     * Обновление сущности по дто
     * @param dto дто
     * @param entity сущность
     */
    void updateEntity(CommentDto dto, @MappingTarget CommentEntity entity);
}
