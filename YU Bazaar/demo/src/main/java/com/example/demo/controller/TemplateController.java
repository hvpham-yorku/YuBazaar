package com.example.demo.controller;

import com.example.demo.Email.EmailSender;
import com.example.demo.Email.EmailTemplate;
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

import java.util.UUID;

@Controller
@SessionAttributes("userName")
public class TemplateController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String showLoginPage() {
        return "login_page";
    }

    // handles login logic
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

    @Autowired
    private EmailSender emailSenderService;

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

            // Validate user inputs
            if (name == null || name.isEmpty()) {
                model.addAttribute("error", "Name is required.");
                return "register_page";
            }

            if (email == null || !email.matches("[a-zA-Z0-9._%+-]+@(yorku\\.ca|my\\.yorku\\.ca)")) {
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

            // Generate recovery code
            String recoveryCode = UUID.randomUUID().toString();  // generates a unique recovery code

            // Create new user
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setAge(age);
            user.setGender(gender);
            user.setDob(dob);
            user.setPassword(password);
            user.setVerified(false); // Explicitly set 'isVerified' to false
            user.setRecoveryCode(recoveryCode);  // store recovery code in the user

            userRepository.save(user);  // save the user

            // Send a confirmation email
            EmailTemplate template = EmailTemplate.REGISTRATION_SUCCESS;
            String subject = template.getSubject();
            String body = template.getBody(name, recoveryCode);

            emailSenderService.sendEmail(email, subject, body);

            return "redirect:/";  // redirect to login page
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

    @PostMapping("/reset_password")
    public String resetPassword(@RequestParam("recoveryCode") String recoveryCode,
                                @RequestParam("newPassword") String newPassword,
                                @RequestParam("confirmNewPassword") String confirmNewPassword,
                                Model model) {
        try {
            // Check if new passwords match
            if (!newPassword.equals(confirmNewPassword)) {
                model.addAttribute("error", "Passwords do not match.");
                return "forgot_password";
            }

            // Find the user by recovery code
            User user = userRepository.findByRecoveryCode(recoveryCode);

            if (user == null) {
                model.addAttribute("error", "Invalid recovery code.");
                return "forgot_password";
            }

            // Update the user's password
            user.setPassword(newPassword);  // Ensure to hash the password in production
            userRepository.save(user);  // Save the updated user

            return "redirect:/";  // Redirect to login page after password reset
        } catch (Exception e) {
            model.addAttribute("error", "Password reset failed: " + e.getMessage());
            return "forgot_password";
        }
    }

    @GetMapping("/home-page")
    public String redirectToHome() {
        return "redirect:/home";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
