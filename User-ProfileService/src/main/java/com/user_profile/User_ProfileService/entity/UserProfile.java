package com.user_profile.User_ProfileService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
public class UserProfile {
    public UserProfile() {
    }

    public UserProfile(String name, String email, String phone, Integer age, Gender gender) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.gender = gender;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false,unique = true)
    String email;

    String phone;

    Integer age;

    @Enumerated(EnumType.STRING)
    Gender gender;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
}
