package com.nikoliadis.swe6002.lostfound.controller;

import com.nikoliadis.swe6002.lostfound.model.User;
import com.nikoliadis.swe6002.lostfound.service.LostItemService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyItemsController {

    private final LostItemService lostItemService;

    public MyItemsController(LostItemService lostItemService) {
        this.lostItemService = lostItemService;
    }

    @GetMapping("/my-items")
    public String myItems(@AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("items", lostItemService.findByUser(currentUser));
        return "lostitems/myitems";
    }
}
