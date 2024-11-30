package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    // View a specific user's profile
    @GetMapping("/profile/{id}")
    public String viewProfile(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
        model.addAttribute("user", user);
        return "profile_view"; // This will use profile_view.html
    }

    // View a list of all users (profiles)
    @GetMapping("/profiles")
    public String viewProfiles(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "profiles_list"; // This will use profiles_list.html
    }
}