package it.univr.services;

import it.univr.component.PasswordHash;
import it.univr.models.PasswordResetToken;
import it.univr.models.User;
import it.univr.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User userExists(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByToken(PasswordResetToken token) {
        return userRepository.findByToken(token);
    }

    public boolean authenticate(String password, User user) {
        return PasswordHash.verifyPassword(password, user.getPassword());
    }

    public void updatePassword(User user, String password) {
        // Hash della password prima di salvarla nel database
        String encodedPassword = PasswordHash.hashPassword(password);

        user.setPassword(encodedPassword);
        user.setAccountLocked(false);
        user.setLoginAttempts(3);
        user.setTempPasswordActive(false);
        save(user);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
