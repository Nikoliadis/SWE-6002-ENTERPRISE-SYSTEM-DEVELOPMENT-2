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
            @RequestParam(value = "q", required = false) String q,
            Model model) {

        String t = (type == null) ? "ALL" : type.toUpperCase();
        String s = (status == null) ? "ALL" : status.toUpperCase();

        List<LostItem> items;

        boolean hasQuery = (q != null && !q.trim().isEmpty());

        if (hasQuery) {
            items = lostItemService.search(q.trim());

            if ("LOST".equals(t)) {
                items = items.stream()
                        .filter(i -> i.getType() != null && i.getType() == ItemType.LOST)
                        .toList();
            } else if ("FOUND".equals(t)) {
                items = items.stream()
                        .filter(i -> i.getType() != null && i.getType() == ItemType.FOUND)
                        .toList();
            }

            if ("OPEN".equals(s)) {
                items = items.stream()
                        .filter(i -> i.getStatus() != null && i.getStatus() == ItemStatus.OPEN)
                        .toList();
            }

        } else {
            boolean filterOpen = "OPEN".equals(s);

            if (filterOpen) {
                if ("LOST".equals(t)) {
                    items = lostItemService.findByTypeAndStatus(ItemType.LOST, ItemStatus.OPEN);
                } else if ("FOUND".equals(t)) {
                    items = lostItemService.findByTypeAndStatus(ItemType.FOUND, ItemStatus.OPEN);
                } else {
                    items = lostItemService.findByStatus(ItemStatus.OPEN);
                }
            } else {
                if ("LOST".equals(t)) {
                    items = lostItemService.findByType(ItemType.LOST);
                } else if ("FOUND".equals(t)) {
                    items = lostItemService.findByType(ItemType.FOUND);
                } else {
                    items = lostItemService.findAllSorted();
                }
            }
        }

        model.addAttribute("items", items);
        model.addAttribute("selectedType", t);
        model.addAttribute("selectedStatus", s);
        model.addAttribute("q", q);

        return "home";
    }
}
