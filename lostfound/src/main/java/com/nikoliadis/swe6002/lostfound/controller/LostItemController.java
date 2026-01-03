package com.nikoliadis.swe6002.lostfound.controller;

import com.nikoliadis.swe6002.lostfound.model.User;
import com.nikoliadis.swe6002.lostfound.service.LostItemService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/lost-items")
public class LostItemController {

    private final LostItemService lostItemService;

    public LostItemController(LostItemService lostItemService) {
        this.lostItemService = lostItemService;
    }

    @GetMapping("/create")
    public String createForm() {
        return "lostitems/create";
    }

    @PostMapping("/create")
    public String create(@RequestParam String title,
                         @RequestParam(required = false) String description,
                         @RequestParam(required = false) String location,
                         @RequestParam(required = false, name = "image") MultipartFile image,
                         @AuthenticationPrincipal User currentUser,
                         Model model) {
        try {
            lostItemService.create(title, description, location, image, currentUser);
            return "redirect:/home?created";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "lostitems/create";
        }
    }
}
