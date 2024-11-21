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
    public String handleRegister(@RequestParam("email") String email,
                                 @RequestParam("password") String password,
                                 Model model) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);

        return "redirect:/";
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