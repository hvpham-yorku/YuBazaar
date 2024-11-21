package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key

    @NotBlank(message = "Name is required.")
    @Size(max = 100, message = "Name cannot exceed 100 characters.")
    private String name;

    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email format.")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+@(yorku\\.ca|my\\.yorku\\.ca)$", message = "Email must be a valid York University email (yorku.ca or my.yorku.ca).")
    @Column(unique = true) // Ensure email uniqueness
    private String email;

    @NotNull(message = "Age is required.")
    @Column(nullable = false)
    private Integer age;

    @NotBlank(message = "Gender is required.")
    private String gender;

    @NotNull(message = "Date of Birth is required.")
    @Column(nullable = false)
    private LocalDate dob;

    @NotBlank(message = "Password is required.")
    @Size(min = 6, message = "Password must be at least 6 characters long.")
    private String password;

    // Constructors
    public User() {
    }

    public User(String name, String email, Integer age, String gender, LocalDate dob, String password) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.dob = dob;
        this.password = password;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}