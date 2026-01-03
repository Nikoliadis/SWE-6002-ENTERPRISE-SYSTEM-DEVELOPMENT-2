package com.nikoliadis.swe6002.lostfound.controller;

import com.nikoliadis.swe6002.lostfound.service.LostItemService;
import com.nikoliadis.swe6002.lostfound.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final LostItemService lostItemService;

    public AdminController(UserService userService, LostItemService lostItemService) {
        this.userService = userService;
        this.lostItemService = lostItemService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model,
                            @RequestParam(value = "tab", required = false, defaultValue = "users") String tab,
                            @RequestParam(value = "error", required = false) String error) {

        model.addAttribute("tab", tab);
        model.addAttribute("error", error);

        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("items", lostItemService.findAll());

        return "admin/dashboard";
    }

    @PostMapping("/users/{id}/toggle-role")
    public String toggleRole(@PathVariable Long id) {
        userService.toggleRole(id);
        return "redirect:/admin/dashboard?tab=users";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id, Authentication authentication) {
        try {
            userService.deleteUser(id, authentication.getName());
            return "redirect:/admin/dashboard?tab=users";
        } catch (RuntimeException ex) {
            return "redirect:/admin/dashboard?tab=users&error=" + ex.getMessage();
        }
    }

    @PostMapping("/items/{id}/delete")
    public String deleteItem(@PathVariable Long id) {
        lostItemService.deleteById(id);
        return "redirect:/admin/dashboard?tab=items";
    }
}
