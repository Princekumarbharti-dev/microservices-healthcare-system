package com.authservice.auth_service.DTO;

public class ValidateResponseDTO {
    public boolean valid;
    public Integer userId;
    public String email;
    public String role;

    public ValidateResponseDTO(boolean valid, Integer userId, String email, String role) {
        this.valid = valid;
        this.userId = userId;
        this.email = email;
        this.role = role;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
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
}
