package it.univr.controllers;

import it.univr.model.User;
import it.univr.service.EmailService;
import it.univr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordForgotController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    public PasswordForgotController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/passwordForgot")
    public String showPasswordForgotPage() {
        return "PasswordForgotPage";
    }

    @PostMapping("/passwordForgot")
    public String resetPasswordForgot(@RequestParam String email, Model model) {
        User user = userService.findByEmail(email);

        if (user != null) {
            model.addAttribute("success", "Ciao '" + user.getUsername() + "'.\n\nSono state mandate le istruzioni a: " + email);
            emailService.sendPasswordResetEmail(user.getEmail());
        }
        else {
            model.addAttribute("error", "Indirizzo email non valido.");
        }

        return "PasswordForgotPage";
    }
}
