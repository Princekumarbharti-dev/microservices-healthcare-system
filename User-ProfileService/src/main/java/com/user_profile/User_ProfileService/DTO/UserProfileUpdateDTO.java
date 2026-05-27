package com.user_profile.User_ProfileService.DTO;

import com.user_profile.User_ProfileService.entity.Gender;
import jakarta.validation.constraints.*;

public class UserProfileUpdateDTO {

    @NotBlank(message = "Name is required")
    private String name;

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

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Integer getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public UserProfileUpdateDTO() {
    }

    public UserProfileUpdateDTO(String name, String phone, Integer age, Gender gender) {
        this.name = name;
        this.phone = phone;
        this.age = age;
        this.gender = gender;
    }
}
