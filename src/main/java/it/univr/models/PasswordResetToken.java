package it.univr.model; 

import jakarta.persitence.*;
import java.time.LocalDateTime;

@Entity
public class PasswordResetToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; 
  private String token; 
  private LocalDateTime expireDate; 
  private Sting userUsername;

  protected PasswordResetToken() {}

  // Getters and Setters

  public String getToken() {return token;}

  public void setToken(String token) {this.token = token;}

  public LocalDateTime getExpiryDate() {return expiryDate;}

  public void setExpiryDate(LocalDateTime expiryDate) {this.expiryDate = expiryDate;}

  public String getUserUsername() {return userUsername;}

  public void setUserUsername(String userUsername) {this.userUsername = userUsername;}
  
}
