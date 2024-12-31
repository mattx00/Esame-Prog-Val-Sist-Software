package it.univr.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PasswordResetToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; 
  private String token; 
  private LocalDateTime expireDate; 
  private String userUsername;

  public PasswordResetToken() {}

  // Getters and Setters

  public String getToken() {return token;}

  public void setToken(String token) {this.token = token;}

  public LocalDateTime getExpiryDate() {return expireDate;}

  public void setExpiryDate(LocalDateTime expiryDate) {this.expireDate = expiryDate;}

  public String getUserUsername() {return userUsername;}

  public void setUserUsername(String userUsername) {this.userUsername = userUsername;}
  
}
