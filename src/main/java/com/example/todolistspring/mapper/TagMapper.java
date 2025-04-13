package com.example.todolistspring.mapper;

import com.example.todolistspring.api.dto.TagDto;
import com.example.todolistspring.store.entities.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Маппер тегов
 */
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TagMapper {

    /**
     * Маппинг в сущность
     * @param dto дто
     * @return сущность
     */
    @Mapping(target = "task", ignore = true)
    TagEntity toEntity(TagDto dto);

    /**
     * Маппинг в дто
     * @param entity сущность
     * @return дто
     */
    TagDto toDto(TagEntity entity);

    /**
     * Обновление сущности по дто
     * @param dto дто
     * @param entity сущность
     */
    @Mapping(target = "task", ignore = true)
    void updateEntity(TagDto dto, @MappingTarget TagEntity entity);
}
