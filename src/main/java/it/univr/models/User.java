package it.univr.models;

import it.univr.enums.Role;
import jakarta.persistence.*;
import org.thymeleaf.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator; 
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name='users')
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    //private String firstName; => come e quando occorre
    //private String lastName;  => come e quando occorre
    private String username;
    private String password;
    private String email;
    private Role role;
    private PasswordReset token;
    private int loginAttempts;
    private boolean accountLocked;
    private boolean privacyAccepted;
    private boolean tempPasswordActive;

    protected User() {}

    public User(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.loginAttempts = 0;
        this.accountLocked = false;
        this.privacyAccepted = false;
    }

    // Deserializzazione utenti da file Users.json
    @JsonCreator
    public User(@JsonProperty('username') String username) {
        this.username = username; 
    }

    // Getters and Setters
   public long getId() {
       return id;
   }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public PasswordResetToken getToken() {
        return token;
    }

    public void setToken(PasswordResetToken token) {
        this.token = token;
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public boolean isPrivacyAccepted() {
        return privacyAccepted;
    }

    public void setPrivacyAccepted(boolean privacyAccepted) {
        this.privacyAccepted = privacyAccepted;
    }

    public boolean isTempPasswordActive() {
        return tempPasswordActive;
    }

    public void setTempPasswordActive(boolean tempPasswordActive) {
        this.tempPasswordActive = tempPasswordActive;
    }
}
