package com.example.demo.controller;

import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        return "index"; // Thymeleaf template name
    }

    @PostMapping("/add-item")
    public String addItem(@RequestParam String name) {
        Item item = new Item();
        item.setName(name);
        itemRepository.save(item);
        return "redirect:/";
    }
}
