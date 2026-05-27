package com.authservice.auth_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "auth_user")
public class AuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private Integer userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(nullable = false)
    private String role;


    public AuthUser(Integer userId, String email, String password, String role) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public AuthUser() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
