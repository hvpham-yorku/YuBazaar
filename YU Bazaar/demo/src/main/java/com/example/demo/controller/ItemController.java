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
        // Validate wear options
        List<String> validWearOptions = Arrays.asList("new", "used (like new)", "used", "poor");
        if (!validWearOptions.contains(wear)) {
            model.addAttribute("error", "Invalid wear condition selected.");
            return "home_page";
        }

        // Validate location
        List<String> validLocations = Arrays.asList(
                "Accolade Building East", "Accolade Building West", "Archives of Ontario", "Atkinson",
                "Norman Bethune College", "Bennett Centre for Student Services", "Bergeron Centre for Engineering Excellence",
                "Behavioural Sciences Building", "Burton Auditorium", "Chemistry Building", "Calumet College",
                "The Joan & Martin Goldfarb Centre for Fine Arts", "Centre for Film and Theatre", "Curtis Lecture Halls",
                "Computer Methods Building", "Central Square", "Central Utilities Building", "Dahdaleh Building",
                "Executive Learning Centre", "Founders College", "Frost Library (Glendon campus)",
                "Farquharson Life Sciences", "Founders Tennis Court", "Glendon Hall (Glendon campus)",
                "Lorna R. Marsden Honours Court & Welcome Centre", "Hart House (Osgoode Hall Law School)",
                "Health, Nursing and Environmental Studies Building", "Hilliard Residence (Glendon campus)",
                "Ignat Kaneff Building", "Kinsmen Building", "Kaneff Tower", "Lassonde Building", "LA&PS @ IBM Markham",
                "Life Sciences Building", "Lumbers Building", "Rob and Cheryl McEwen Graduate Study & Research Building",
                "McLaughlin College", "Off Campus", "Physical Resources Building",
                "Petrie Science and Engineering Building / Petrie Observatory", "Ross Building - North wing",
                "Ross Building - South wing", "Seneca @ York", "Stong College", "Scott Library",
                "Sherman Health Science Research Centre", "Stedman Lecture Halls", "Seymour Schulich Building",
                "Sheridan College - Trafalgar Campus", "Student Centre", "Steacie Science and Engineering Library",
                "Tennis Canada", "Technology and Enhanced Learning Building", "Track and Field Centre",
                "Tait McKenzie Centre", "Tait Tennis Courts", "Vanier College", "Vari Hall", "Winters College",
                "West Office Building", "William Small Centre", "York Hall (Glendon campus)", "York Lanes"
        );


        if (!validLocations.contains(location)) {
            model.addAttribute("error", "Invalid location selected.");
            return "home_page";
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

