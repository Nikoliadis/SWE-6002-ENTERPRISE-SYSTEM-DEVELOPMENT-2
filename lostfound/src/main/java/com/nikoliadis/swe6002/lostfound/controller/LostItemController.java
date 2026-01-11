package com.nikoliadis.swe6002.lostfound.controller;

import com.nikoliadis.swe6002.lostfound.model.ItemType;
import com.nikoliadis.swe6002.lostfound.model.LostItem;
import com.nikoliadis.swe6002.lostfound.model.User;
import com.nikoliadis.swe6002.lostfound.service.LostItemService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

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
                         @RequestParam("type") ItemType type,
                         @RequestParam(required = false) MultipartFile image,
                         @AuthenticationPrincipal User currentUser,
                         Model model) {

        try {
            lostItemService.create(title, description, location, type, image, currentUser);
            return "redirect:/home?created";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "lostitems/create";
        }
    }

    // ✅ My Items page: /lost-items/my-items
    @GetMapping("/my-items")
    public String myItems(@AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("items", lostItemService.findByUser(currentUser));
        return "lostitems/myitems";
    }

    // ✅ Delete item: POST /lost-items/{id}/delete
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
                         @AuthenticationPrincipal User currentUser) {

        Optional<LostItem> opt = lostItemService.findById(id);

        if (opt.isEmpty()) {
            return "redirect:/lost-items/my-items?error=notfound";
        }

        LostItem item = opt.get();

        boolean isOwner = item.getUser() != null
                && item.getUser().getId().equals(currentUser.getId());

        boolean isAdmin = currentUser.getRole() != null
                && currentUser.getRole().equals("ROLE_ADMIN");

        if (!isOwner && !isAdmin) {
            return "redirect:/lost-items/my-items?error=forbidden";
        }

        lostItemService.deleteById(id);
        return "redirect:/lost-items/my-items?deleted";
    }
}
