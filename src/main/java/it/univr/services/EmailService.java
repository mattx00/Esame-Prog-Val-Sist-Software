package it.univr.services;

import it.univr.models.PasswordResetToken;
import it.univr.models.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailService {

    private final List<SimpleMailMessage> sentEmails = new ArrayList<>();
    private final UserService userService;
    private final PasswordResetTokenService passwordTokenService;

    public EmailService(UserService userService, PasswordResetTokenService passwordTokenService) {
        this.userService = userService;
        this.passwordTokenService = passwordTokenService;
    }

    public void sendEmail(SimpleMailMessage message) {
        sentEmails.add(message);
    }

    // Metodo principale per invio email
    public void sendEmailForPurpose(String toEmail, String subject, String emailBodyTemplate) {
        User user = userService.findByEmail(toEmail);
        PasswordResetToken passwordResetToken = passwordTokenService.getANewToken(user);

        user.setToken(passwordResetToken);
        userService.save(user);

        String resetUrl = "http://localhost:8080/resetPassword?token=" + passwordResetToken.getToken();
        String emailBody = String.format(emailBodyTemplate, user.getUsername(), user.getPassword(), resetUrl);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(emailBody);

        sendEmail(message);
        System.out.println("------------------------------------\n\n" + "EMAIL TO: " + toEmail + "\n\n" + emailBody + "\n\n------------------------------------");
    }

    // Invio email per il reset della password
    public void sendPasswordResetEmail(String toEmail) {
        String subject = "Ripristino della password";
        String emailBodyTemplate = "Gentile utente, \n\n"
                + "Per ripristinare la tua password, occorre cliccare il seguente link:\n"
                + "%3$s\n"
                + "Se non sei stato tu a richiedere il ripristino della password, ignora quest'email.";
        sendEmailForPurpose(toEmail, subject, emailBodyTemplate);
    }

    // Invio email per la password temporanea
    public void sendTempPasswordEmail(String toEmail) {
        String subject = "Credenziali account";
        String emailBodyTemplate = "Gentile utente, \n\n"
                + "È stato attivato il suo account con le seguenti credenziali:\n\n"
                + "Nome utente: %1$s\n"
                + "Password: %2$s\n\n"
                + "La password è di uso temporaneo, al primo login verrà chiesto di modificarla per proseguire con l'uso della piattaforma.\n"
                + "È possibile effettuare il cambio password anche mediante il seguente link:\n\n"
                + "%3$s";
        sendEmailForPurpose(toEmail, subject, emailBodyTemplate);
    }
}