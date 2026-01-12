package com.nikoliadis.swe6002.lostfound.controller;

import com.nikoliadis.swe6002.lostfound.service.LostItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/items")
public class ItemDetailsController {

    private final LostItemService lostItemService;

    public ItemDetailsController(LostItemService lostItemService) {
        this.lostItemService = lostItemService;
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {

        var opt = lostItemService.findById(id);
        if (opt.isEmpty()) {
            return "redirect:/home?error=notfound";
        }

        model.addAttribute("item", opt.get());
        return "lostitems/details";
    }
}
