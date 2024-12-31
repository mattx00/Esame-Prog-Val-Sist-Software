package it.univr.controllers;

import it.univr.model.User;
import it.univr.service.EmailService;
import it.univr.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/login")
    public String showLoginPage(Model model, HttpSession session) {
        model.addAttribute("usernameChecked", false);
        return "LoginPage";
    }

    @PostMapping("/login")
    public String performLogin(@RequestParam String username, @RequestParam(required = false) String password, Model model, HttpSession session) {
        User user = userService.userExists(username);

        // Verifica esistenza utente
        if (password == null) {
            if (user != null) {
                // Verifica account bloccato
                if (user.isAccountLocked()) {
                    model.addAttribute("error", "L'account utente è stato temporaneamente sospseso.\n\nVerranno inviate tramite email le istruzioni utili al ripristino.");
                    model.addAttribute("usernameChecked", false);
                    model.addAttribute("username", username);
                    emailService.sendPasswordResetEmail(user.getEmail());
                }
                // Nome utente esistente
                else {
                    model.addAttribute("usernameChecked", true);
                    model.addAttribute("username", username);
                }
            }
            else {
                model.addAttribute("error", "Nome utente non valido! Contattare l'amministratore per un'eventuale attivazione in sospeso.");
                model.addAttribute("usernameChecked", false);
            }
        }
        // Autenticazione utente
        else {
            // Password valida
            if (userService.authenticate(password, user)) {
                // Login riuscito: ripristino dei tentativi e redirect alla Dashboard
                user.setLoginAttempts(3);
                userService.save(user);
                session.setAttribute("user", user);

                // Utente deve creare una password definitiva
                if (user.isTempPasswordActive()){
                    return "redirect:/resetPassword?token=" + user.getToken().getToken();
                }
                // Utente deve accettare la normativa sulla privacy
                else if (!user.isPrivacyAccepted()) {
                    return "redirect:/privacy";
                }
                else {
                    return "redirect:/dashboard";
                }
            }
            else {
                // Password errata: numero di tentativi rimasti
                if (user.getLoginAttempts() > 1) {
                    user.setLoginAttempts(user.getLoginAttempts() - 1);
                    model.addAttribute("error", "Password errata.\n\n" + user.getLoginAttempts() + " tentativi rimasti.");
                    model.addAttribute("usernameChecked", true);
                    model.addAttribute("username", username);
                }
                // Tenativi esauriti: blocco dell'account
                else {
                    user.setAccountLocked(true);
                    model.addAttribute("error", "L'account utente è stato temporaneamente sospseso.\n\nVerranno inviate tramite email le istruzioni utili al ripristino.");
                    model.addAttribute("usernameChecked", false);
                    model.addAttribute("username", username);
                    emailService.sendPasswordResetEmail(user.getEmail());
                }

                userService.save(user);
            }
        }

        return "LoginPage";
    }
}
