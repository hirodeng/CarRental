package com.carrental.bookingservice.view;

import com.carrental.bookingservice.modules.user.entity.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {

  private String name;
  private String password;

  public User toUser(PasswordEncoder passwordEncoder) {
    return new User(name, passwordEncoder.encode(password));
  }
  
}
