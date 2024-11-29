package com.example.demo.controller;
import java.util.Arrays;
import java.util.List;
import com.example.demo.Email.EmailSender;
import com.example.demo.Email.EmailTemplate;
import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepository;
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

    @Autowired
    private EmailSender emailSender; // Added EmailSender
    
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
                          @RequestParam String description,
                          @RequestParam String sellerEmail,
                          Model model) {
        // Validate location
        List<String> validLocations = Arrays.asList("ACE", "ACW", "AO", "ATK", "BC", "BCSS", "BRG", "BSB", "BU",
                "CB", "CC", "CFA", "CFT", "CLH", "CMB", "CSQ", "CUB", "DB", "ELC", "FC", "FL", "FRQ", "FTC", "GH",
                "HC", "HH", "HNE", "HR", "IKB", "K", "KT", "LAS", "LMP", "LSB", "LUM", "MB", "MC", "OC", "PR", "PSE",
                "R N", "R S", "SAY", "SC", "SCL", "SHR", "SLH", "SSB", "ST", "STC", "STL", "TC Sobeys", "TEL", "TFC",
                "TM Tait", "TTC", "VC", "VH", "WC", "WOB", "WSC", "YH", "YL");

        if (!validLocations.contains(location)) {
            model.addAttribute("error", "Invalid location selected.");
            return "home_page"; // Or another error page
        }

        Item item = new Item();
        item.setTitle(title);
        item.setPrice(price);
        item.setWear(wear);
        item.setLocation(location);
        item.setDescription(description);
        itemRepository.save(item);

        EmailTemplate template = EmailTemplate.LISTING_CONFIRMATION;
        String subject = template.getSubject();
        String body = template.getBody(title); // Use the item's title in the email body
        
        emailSender.sendEmail(sellerEmail, subject, body);
        model.addAttribute("success", "Item added successfully! A confirmation email has been sent.");
        
        return "redirect:/home";
    }

    @PostMapping("/delete-item")
    public String deleteItem(@RequestParam Long id) {
        itemRepository.deleteById(id);
        return "redirect:/home";
    }

}

