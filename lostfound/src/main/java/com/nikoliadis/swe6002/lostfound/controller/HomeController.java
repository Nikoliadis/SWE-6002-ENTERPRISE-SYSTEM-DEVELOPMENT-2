package com.nikoliadis.swe6002.lostfound.controller;

import com.nikoliadis.swe6002.lostfound.model.ItemStatus;
import com.nikoliadis.swe6002.lostfound.model.ItemType;
import com.nikoliadis.swe6002.lostfound.model.LostItem;
import com.nikoliadis.swe6002.lostfound.service.LostItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private final LostItemService lostItemService;

    public HomeController(LostItemService lostItemService) {
        this.lostItemService = lostItemService;
    }

    @GetMapping({"/", "/home"})
    public String home(
            @RequestParam(defaultValue = "ALL") String type,
            @RequestParam(defaultValue = "ALL") String status,
            Model model) {

        String t = type == null ? "ALL" : type.toUpperCase();
        String s = status == null ? "ALL" : status.toUpperCase();

        List<LostItem> items;

        boolean filterOpen = "OPEN".equals(s);

        if (filterOpen) {
            // status = OPEN
            if ("LOST".equals(t)) {
                items = lostItemService.findByTypeAndStatus(ItemType.LOST, ItemStatus.OPEN);
            } else if ("FOUND".equals(t)) {
                items = lostItemService.findByTypeAndStatus(ItemType.FOUND, ItemStatus.OPEN);
            } else {
                items = lostItemService.findByStatus(ItemStatus.OPEN);
            }
        } else {
            // status = ALL
            if ("LOST".equals(t)) {
                items = lostItemService.findByType(ItemType.LOST);
            } else if ("FOUND".equals(t)) {
                items = lostItemService.findByType(ItemType.FOUND);
            } else {
                items = lostItemService.findAllSorted();
            }
        }

        model.addAttribute("items", items);
        model.addAttribute("selectedType", t);
        model.addAttribute("selectedStatus", s);

        return "home";
    }
}
