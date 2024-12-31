package it.univr.services;

import it.univr.models.PasswordResetToken;
import it.univr.models.User;
import it.univr.repositories.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PasswordResetTokenService {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    public List<PasswordResetToken> findAll() {
        return passwordResetTokenRepository.findAll();
    }

    public PasswordResetToken getANewToken(User user) {
        PasswordResetToken token;

        if (user.getToken() != null) {
            token = user.getToken();
        }
        else {
            token = new PasswordResetToken();
        }

        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(LocalDateTime.now().plusSeconds(30));
        token.setUserUsername(user.getUsername());
        return token;
    }

    public PasswordResetToken findByStringToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }
}
