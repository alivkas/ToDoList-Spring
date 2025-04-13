package com.example.todolistspring.api.dto;

import com.example.todolistspring.store.enums.UserRole;

/**
 * Дто роли
 * @param role роль
 */
public record RoleDto(UserRole role) {
}
