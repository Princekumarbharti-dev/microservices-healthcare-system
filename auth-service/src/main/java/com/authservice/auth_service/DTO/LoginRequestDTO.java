package com.authservice.auth_service.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {

    @NotBlank
    @Email
    public String email;

    @NotBlank
    public String password;

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

    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginRequestDTO() {

    }


}
