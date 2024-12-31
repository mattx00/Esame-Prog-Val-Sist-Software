package it.univr.controller;

import it.univr.model.PasswordResetToken;
import it.univr.model.User;
import it.univr.service.PasswordResetTokenService;
import it.univr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ResetPasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordResetTokenService passwordTokenService;

    @GetMapping("/resetPassword")
    public String showResetPasswordPage(String token, Model model) {
        PasswordResetToken passToken = passwordTokenService.findByStringToken(token);

        if (passToken != null) {
            if(!passToken.getExpiryDate().isAfter(LocalDateTime.now())) {
                model.addAttribute("error", "Il token è scaduto");
                return "ErrorPage";
            }

            User user = userService.findByToken(passToken);

            if (user != null) {
                model.addAttribute("token", token);
                return "ResetPasswordPage";
            }
            else {
                model.addAttribute("error", "Non è stato possibile identificare l'utente");
            }
        }

        return "ErrorPage";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam String token, @RequestParam String password, @RequestParam String confpassword, Model model) {
        PasswordResetToken passToken = passwordTokenService.findByStringToken(token);
        User user = userService.findByToken(passToken);

        // Password e conferma password diverse
        if (!password.equals(confpassword)) {
            model.addAttribute("error", "Le password non combaciano.");
            model.addAttribute("token", token);
            return "ResetPasswordPage";
        }

        // Controllo formato password
        if (password.length() < 8) {
            model.addAttribute("error", "La password deve essere lunga almeno 8 caratteri.");
            model.addAttribute("token", token);
            return "ResetPasswordPage";
        }
        else if (!password.matches(".*[A-Z].*")) {
            model.addAttribute("error", "La password deve contenere almeno una lettera maiuscola.");
            model.addAttribute("token", token);
            return "ResetPasswordPage";
        }
        else if (!password.matches(".*[a-z].*")) {
            model.addAttribute("error", "La password deve contenere almeno una lettera minuscola.");
            model.addAttribute("token", token);
            return "ResetPasswordPage";
        }
        else if (!password.matches(".*[0-9].*")) {
            model.addAttribute("error", "La password deve contenere almeno un numero.");
            model.addAttribute("token", token);
            return "ResetPasswordPage";
        }
        else if (!password.matches(".*[@#$%^*&+=!].*")) {
            model.addAttribute("error", "La password deve contenere almeno un carattere speciale.");
            model.addAttribute("token", token);
            return "ResetPasswordPage";
        }

        if (user != null) {
            userService.updatePassword(user, password);
            model.addAttribute("success", "La password è stata aggiornata.\n\n Se l'account fosse stato disattivato per motivi di sicurezza, ora è nuovamente attivo.");
            return "ResetPasswordPage";
        }

        model.addAttribute("error", "La pagina non è più disponibile");
        return "ErrorPage";
    }
}
