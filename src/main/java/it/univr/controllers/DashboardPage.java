package it.univr.controllers;

import it.univr.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String showDashboardPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        model.addAttribute("userRole", user.getRole());
        model.addAttribute("username", user.getUsername());

        return "DashboardPage";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "LoginPage";
    }
}
