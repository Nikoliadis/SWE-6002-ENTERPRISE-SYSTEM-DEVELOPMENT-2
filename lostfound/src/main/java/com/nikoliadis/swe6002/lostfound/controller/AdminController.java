package com.nikoliadis.swe6002.lostfound.controller;

import com.nikoliadis.swe6002.lostfound.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model,
                            @RequestParam(value = "error", required = false) String error) {

        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("error", error);
        return "admin/dashboard";
    }

    @PostMapping("/users/{id}/toggle-role")
    public String toggleRole(@PathVariable Long id) {
        userService.toggleRole(id);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id, Authentication authentication) {
        try {
            userService.deleteUser(id, authentication.getName());
            return "redirect:/admin/dashboard";
        } catch (RuntimeException ex) {
            return "redirect:/admin/dashboard?error=" + ex.getMessage();
        }
    }
}
