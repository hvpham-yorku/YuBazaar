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

    @GetMapping("/home")
    public String viewHomePage(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        return "home_page";
    }

    @PostMapping("/add-item")
    public String addItem(@RequestParam String title,
                          @RequestParam double price,
                          @RequestParam String wear,
                          @RequestParam String location,
                          @RequestParam String description) {
        Item item = new Item();
        item.setTitle(title);
        item.setPrice(price);
        item.setWear(wear);
        item.setLocation(location);
        item.setDescription(description);
        itemRepository.save(item);
        return "redirect:/home";
    }

    @PostMapping("/delete-item")
    public String deleteItem(@RequestParam Long id) {
        itemRepository.deleteById(id);
        return "redirect:/home";
    }

}

