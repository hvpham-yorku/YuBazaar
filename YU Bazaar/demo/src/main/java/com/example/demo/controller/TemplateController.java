package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TemplateController {

    @Autowired
    private UserService userService;

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
    public String showRegisterPage(Model model) {
        model.addAttribute("user",new User());
        return "register_page"; // Maps to register.html in templates
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("user") User user, Model model ) {
        boolean status = userService.register(user);

        if(status){
            model.addAttribute("successMsg","User Registration Successful");
        }
        else {
            model.addAttribute("errorMsg", "User not Registered due to some error");
        }

        return "redirect:/login"; // Redirect to login page after successful registration
    }
    @GetMapping("/forgot_password")
    public String forgotPassword(){
        return "forgot_password";
    }
    @GetMapping("/home")
    public String showHome(){
        return "home_page";
    }

}
