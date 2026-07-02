package com.example.cricketbooking.controller;

import com.example.cricketbooking.User;
import com.example.cricketbooking.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        User user = userService.authenticate(email, password);
        if (user == null) {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }

        session.setAttribute("userId", user.getId());
        session.setAttribute("sessionName", user.getName());
        session.setAttribute("sessionEmail", user.getEmail());
        session.setAttribute("role", user.getRole());

        if ("ADMIN".equals(user.getRole())) {
            return "redirect:/admin/dashboard";
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String mobileNumber,
                           @RequestParam String password,
                           Model model) {
        if (userService.existsByEmail(email)) {
            model.addAttribute("error", "Email already registered");
            return "register";
        }

        if (userService.existsByMobileNumber(mobileNumber)) {
            model.addAttribute("error", "Mobile number already registered");
            return "register";
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setMobileNumber(mobileNumber);
        user.setPassword(password);
        userService.register(user);

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
