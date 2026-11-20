package com.learnforge.controller;

import com.learnforge.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** Home page, login page and student registration. (The POST /login itself is handled by Spring Security.) */
@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String fullName,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           Model model,
                           RedirectAttributes redirectAttributes) {

        // keep the typed values so the form is not emptied after an error
        model.addAttribute("fullName", fullName);
        model.addAttribute("email", email);

        if (fullName.isBlank() || email.isBlank() || password.isBlank()) {
            model.addAttribute("error", "All fields are required.");
            return "register";
        }
        if (!email.trim().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            model.addAttribute("error", "Please enter a valid email address.");
            return "register";
        }
        if (password.length() < 6) {
            model.addAttribute("error", "Password must be at least 6 characters long.");
            return "register";
        }
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "register";
        }

        try {
            userService.registerStudent(fullName, email, password);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }

        redirectAttributes.addFlashAttribute("message", "Registration successful. Please log in.");
        return "redirect:/login";
    }
}
