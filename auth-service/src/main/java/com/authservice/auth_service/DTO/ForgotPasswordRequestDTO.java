package com.authservice.auth_service.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ForgotPasswordRequestDTO {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "New password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String newPassword;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;

    public ForgotPasswordRequestDTO() {
    }

    public ForgotPasswordRequestDTO(String email, String newPassword, String confirmPassword) {
        this.email = email;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}