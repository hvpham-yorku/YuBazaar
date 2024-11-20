package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TemplateController {
    @GetMapping("/")
    public String showLoginPage() {
        return "login_page";
    }

    @PostMapping("/")
    public String handleLogin() {
        // Add login logic here (authentication)
        return "redirect:/home";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register_page"; // Maps to register.html in templates
    }

    @PostMapping("/register")
    public String handleRegister() {
        // Add registration logic here (e.g., save user to DB)
        return "redirect:/login"; // Redirect to login page after successful registration
    }
    @GetMapping("/forgot_password")
    public String forgotPassword(){
        return "forgot_password";
    }

}
