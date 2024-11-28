package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Name is required.")
    @Column(name = "name", nullable = false)
    private String name;

    @Email(message = "Invalid email format.")
    @NotBlank(message = "Email is required.")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+@(yorku\\.ca|my\\.yorku\\.ca)$", message = "Email must be a valid York University email (yorku.ca or my.yorku.ca).")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters.")
    @NotBlank(message = "Password is required.")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "dob", nullable = false)
    private String dob;

    @Column(name = "recovery_code", nullable = true)
    private String recoveryCode;
    
    @Column(name = "is_verified", nullable = false)
    private boolean isVerified = false; // Default value


    @Column(name = "otp", nullable = true)
    private String otp;

    @Column(name = "is_verified", nullable = false)
    private boolean isVerified = false;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRecoveryCode() {
        return recoveryCode;
    }

    public void setRecoveryCode(String recoveryCode) {
        this.recoveryCode = recoveryCode;
    }
    
    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        this.isVerified = verified;
    }
    
}
