package com.nikoliadis.swe6002.lostfound.controller;

import com.nikoliadis.swe6002.lostfound.model.ItemType;
import com.nikoliadis.swe6002.lostfound.service.LostItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final LostItemService lostItemService;

    public HomeController(LostItemService lostItemService) {
        this.lostItemService = lostItemService;
    }

    @GetMapping({"/", "/home"})
    public String home(@RequestParam(value = "type", defaultValue = "ALL") String type,
                       Model model) {

        if ("LOST".equalsIgnoreCase(type)) {
            model.addAttribute("items", lostItemService.findByType(ItemType.LOST));
            model.addAttribute("selectedType", "LOST");
        } else if ("FOUND".equalsIgnoreCase(type)) {
            model.addAttribute("items", lostItemService.findByType(ItemType.FOUND));
            model.addAttribute("selectedType", "FOUND");
        } else {
            model.addAttribute("items", lostItemService.findAllSorted());
            model.addAttribute("selectedType", "ALL");
        }

        return "home";
    }
}
