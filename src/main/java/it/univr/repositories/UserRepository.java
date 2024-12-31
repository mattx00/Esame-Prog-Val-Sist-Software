package it.univr.repositories;

import it.univr.enums.Role;
import it.univr.models.PasswordResetToken;
import it.univr.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    User findByEmail(String email);
    User findByToken(PasswordResetToken token);
    List<User> findByRole(Role role);
}
