package com.kondziorno.simplebudget.service;

import com.kondziorno.simplebudget.dto.UserRegistrationDto;
import com.kondziorno.simplebudget.model.User;
import com.kondziorno.simplebudget.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User registerUser(UserRegistrationDto registrationDto) {
    if (userRepository.existsByUsername(registrationDto.getUsername())) {
      throw new RuntimeException("Nazwa użytkownika już istnieje");
    }

    if (userRepository.existsByEmail(registrationDto.getEmail())) {
      throw new RuntimeException("Email już jest używany");
    }

    if (!registrationDto.isPasswordMatching()) {
      throw new RuntimeException("Hasła nie są identyczne");
    }

    User user = new User();
    user.setUsername(registrationDto.getUsername());
    user.setEmail(registrationDto.getEmail());
    user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
    user.setFirstName(registrationDto.getFirstName());
    user.setLastName(registrationDto.getLastName());
    user.setRole(User.Role.USER);

    return userRepository.save(user);
  }

  public User findByUsername(String username) {
    return userRepository.findByUsername(username).orElse(null);
  }

  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }
}
