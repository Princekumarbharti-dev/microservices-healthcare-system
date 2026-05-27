package com.authservice.auth_service.DTO;

public class LoginResponseDTO {
    public String token;
    public Integer userId;
    public String email;
    public String role;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, Integer userId, String email, String role) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.role = role;
    }
}
