package com.example.todolistspring.store.entities;

import com.example.todolistspring.store.entities.base.BasicEntity;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * Сущность пользователя
 */
@Entity
@Table(name = "tbl_user")
public class UserEntity extends BasicEntity implements UserDetails {

    @Column(name = "username")
    private String username;
    @Column(name = "passwords")
    private String password;
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TaskEntity> tasks = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ReportEntity> reports = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tbl_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    /**
     * Конструктор для инициализации
     */
    public UserEntity() {
    }

    /**
     * Конуструктор для создания сущности
     * @param username имя пользователя
     * @param password пароль
     * @param email почта
     */
    public UserEntity(String username,
                      String password,
                      String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    /**
     * Получить пароль
     * @return пароль
     */
    public String getPassword() {
        return password;
    }

    /**
     * Установить пароль
     * @param password пароль
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Получить имя пользователя
     * @return имя пользователя
     */
    public String getUsername() {
        return username;
    }

    /**
     * Установить имя пользователя
     * @param username имя пользователя
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Получить почту
     * @return почта
     */
    public String getEmail() {
        return email;
    }

    /**
     * Установить почту
     * @param email почта
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Получить множество ролей
     * @return множество ролей
     */
    public Set<RoleEntity> getRoles() {
        return roles;
    }

    /**
     * Установить множество ролей
     * @param roles множество ролей
     */
    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }
}
