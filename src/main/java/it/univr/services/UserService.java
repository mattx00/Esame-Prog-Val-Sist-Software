package it.univr.services;

import it.univr.component.PasswordHash;
import it.univr.model.PasswordResetToken;
import it.univr.model.User;
import it.univr.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

    public List<User> findByRole(String token) {return userRepository.findByRole(token);}

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
