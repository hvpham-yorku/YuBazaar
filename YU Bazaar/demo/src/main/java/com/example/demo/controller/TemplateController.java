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

    @Autowired
    private EmailSender emailSenderService;

    @GetMapping("/")
    public String showLoginPage() {
        return "login_page";
    }

    // Handles login logic
    @PostMapping("/login")
    public String handleLogin(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              Model model) {
        User user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            if (!user.isVerified()) {
                model.addAttribute("error", "Account is not verified. Please check your email.");
                return "login_page";
            }
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

            // Generate OTP
            String otp = generateOTP();

            // Generate recovery code
            String recoveryCode = UUID.randomUUID().toString();  // Generates a unique recovery code

            // Create new user
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setAge(age);
            user.setGender(gender);
            user.setDob(dob);
            user.setPassword(password);
            user.setOtp(otp);                       // Store the generated OTP
            user.setVerified(false);                // Default to not verified
            user.setRecoveryCode(recoveryCode);     // Store recovery code in the user

            userRepository.save(user);  // Save the user

            // Send OTP Email
            emailSenderService.sendOtpEmail(email, otp);

            model.addAttribute("success", "Registration successful! Check your email for the OTP to verify your account.");
            return "redirect:/verify";

        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            e.printStackTrace();
            return "register_page";
        }
    }

    private String generateOTP() {
        int otp = 100000 + (int) (Math.random() * 900000); // Generates a number between 100000 and 999999
        return String.valueOf(otp);
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

    @PostMapping("/verify")
    public String verifyOtp(@RequestParam("email") String email,
                            @RequestParam("otp") String otp,
                            Model model) {
        try {
            User user = userRepository.findByEmail(email);

            if (user == null) {
                model.addAttribute("error", "User not found.");
                return "verify_otp"; // Redirect to OTP verification page
            }

            if (!user.getOtp().equals(otp)) {
                model.addAttribute("error", "Invalid OTP.");
                return "verify_otp"; // Redirect to OTP verification page
            }

            // Mark user as verified
            user.setVerified(true);
            user.setOtp(null); // Clear the OTP after verification
            userRepository.save(user);

            model.addAttribute("success", "Account verified successfully! You can now log in.");
            return "redirect:/"; // Redirect to login page
        } catch (Exception e) {
            model.addAttribute("error", "OTP verification failed: " + e.getMessage());
            return "verify_otp"; // Redirect to OTP verification page
        }
    }

    @GetMapping("/verify")
    public String showVerifyPage() {
        return "verify_otp"; // Return the OTP verification template
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
