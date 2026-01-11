package com.nikoliadis.swe6002.lostfound.controller;

import com.nikoliadis.swe6002.lostfound.service.LostItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ItemDetailsController {

    private final LostItemService lostItemService;

    public ItemDetailsController(LostItemService lostItemService) {
        this.lostItemService = lostItemService;
    }

    @GetMapping("/items/{id}")
    public String details(@PathVariable Long id, Model model) {

        var itemOpt = lostItemService.findById(id);
        if (itemOpt.isEmpty()) {
            return "redirect:/home?error=notfound";
        }

        model.addAttribute("item", itemOpt.get());
        return "items/details";
    }
}
