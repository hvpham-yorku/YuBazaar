package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@SessionAttributes("userName")
public class TemplateController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String showLoginPage() {
        return "login_page";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              Model model) {
        User user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            model.addAttribute("userName", user.getName());
            return "redirect:/home";
        } else {

            model.addAttribute("error", "Invalid email or password");
            return "login_page";
        }
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register_page";
    }

    @PostMapping("/register")
    public String handleRegister(@RequestParam("name") String name,
                                 @RequestParam("email") String email,
                                 @RequestParam("age") int age,
                                 @RequestParam("gender") String gender,
                                 @RequestParam("dob") String dob,
                                 @RequestParam("password") String password,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Model model) {
        try {

            if (!password.equals(confirmPassword)) {
                model.addAttribute("error", "Passwords do not match");
                return "register_page";
            }

            if (name == null || name.isEmpty()) {
                model.addAttribute("error", "Name is required.");
                return "register_page";
            }

            if (email == null || !email.matches("[a-zA-Z0-9._%+-]+@yorku\\.ca")) {
                model.addAttribute("error", "Invalid email format.");
                return "register_page";
            }

            if (age <= 0) {
                model.addAttribute("error", "Age must be a positive number.");
                return "register_page";
            }

            if (userRepository.findByEmail(email) != null) {
                model.addAttribute("error", "Email is already registered.");
                return "register_page";
            }

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setAge(age);
            user.setGender(gender);
            user.setDob(dob);
            user.setPassword(password);

            userRepository.save(user);

            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            e.printStackTrace();
            return "register_page";
        }
    }

    @GetMapping("/forgot_password")
    public String forgotPassword() {
        return "forgot_password";
    }

    @GetMapping("/home")
    public String showHome() {
        return "home_page";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}