package com.example.todolistspring.store.entities;

import com.example.todolistspring.store.entities.base.BasicEntity;
import com.example.todolistspring.store.enums.UserRole;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_role")
public class RoleEntity extends BasicEntity implements GrantedAuthority {

    @Enumerated(EnumType.STRING)
    @Column(name = "Role", unique = true, nullable = false)
    private UserRole role;

    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users = new HashSet<>();

    /**
     * Конструктор для инициализации
     */
    public RoleEntity() {
    }

    /**
     * Конуструктор для создания сущности
     * @param role роль
     */
    public RoleEntity(UserRole role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.name();
    }

    /**
     * Получить ролль
     * @return роль
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * Установить роль
     * @param role роль
     */
    public void setRole(UserRole role) {
        this.role = role;
    }

    /**
     * Получить множество пользователей
     * @return
     */
    public Set<UserEntity> getUsers() {
        return users;
    }

    /**
     * Установить множество пользователей
     * @param users множество пользователей
     */
    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }
}
