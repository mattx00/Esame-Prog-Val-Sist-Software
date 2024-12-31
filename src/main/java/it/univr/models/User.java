package it.univr.models;

import it.univr.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.thymeleaf.util.StringUtils;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    // private String username; // get() => firstName.lastName?
    private String password;
    private String email;
    private Role role;
    private String token;
    private int loginAttempts;
    private boolean accountLocked;
    private boolean privacyAccepted;

    protected User() {}

    public User(String firstName, String lastName, String password, String email, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        // TODO: hash password
        this.password = password;
        this.email = email;
        this.role = role;
        this.loginAttempts = 0;
        this.accountLocked = false;
        this.privacyAccepted = false;
    }
}
