package com.example.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
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
            // Successful login
            return "redirect:/home";
        } else {
            // Login failed
            model.addAttribute("error", "Invalid email or password");
            return "login_page";
        }
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register_page"; // Maps to register.html in templates
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
            // Check if passwords match
            if (!password.equals(confirmPassword)) {
                model.addAttribute("error", "Passwords do not match");
                return "register_page";
            }

            // Validate fields
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

            // Check if email is already in use
            if (userRepository.findByEmail(email) != null) {
                model.addAttribute("error", "Email is already registered.");
                return "register_page";
            }

            // Create user object
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setAge(age);
            user.setGender(gender);
            user.setDob(dob);
            user.setPassword(password); // Consider hashing in a production environment

            // Save to database
            userRepository.save(user);

            return "redirect:/";  // Redirect to login page after successful registration
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            e.printStackTrace(); // You might want to replace this with proper logging
            return "register_page";
        }
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