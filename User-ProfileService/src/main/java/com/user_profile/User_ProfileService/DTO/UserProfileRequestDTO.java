package com.user_profile.User_ProfileService.DTO;

import com.user_profile.User_ProfileService.entity.Gender;
import jakarta.validation.constraints.*;
import lombok.*;

public class UserProfileRequestDTO {

    public UserProfileRequestDTO() {
    }

    public UserProfileRequestDTO(String name, String email, String phone, Integer age, Gender gender, String password, String confirmPassword) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.gender = gender;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(
            regexp = "^[1-9]\\d{9}$",
            message = "Phone number must be 10 digits and start with 1-9"
    )
    private String phone;

    @NotNull(message = "Age is required")
    @Min(value = 1, message = "Age must be greater than 0")
    @Max(value = 110, message = "Age cannot exceed 110")
    private Integer age;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Confirm Password is required")
    private String confirmPassword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}