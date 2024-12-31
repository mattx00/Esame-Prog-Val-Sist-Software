package it.univr.controller;

import it.univr.model.User;
import it.univr.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PrivacyController {

    private final UserService userService;

    public PrivacyController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/privacy")
    public String showPrivacyPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("username", user.getUsername());
        return "PrivacyPage";
    }

    @PostMapping("/acceptPrivacy")
    public String acceptPrivacy(@RequestParam("username") String username) {
        User user = userService.userExists(username);

        user.setPrivacyAccepted(true);
        userService.save(user);
        return "redirect:/dashboard";
    }

    @PostMapping("/declinePrivacy")
    public String declinePrivacy(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/login";
    }
}
