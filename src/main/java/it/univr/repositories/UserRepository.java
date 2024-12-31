package it.univr.repositories;

import it.univr.model.PasswordResetToken;
import it.univr.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    User findByEmail(String email);
    User findByToken(PasswordResetToken token);
    List<User> findByRole(String role);
}
