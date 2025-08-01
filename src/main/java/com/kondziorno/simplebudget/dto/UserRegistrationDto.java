package com.kondziorno.simplebudget.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRegistrationDto {

  @NotBlank(message = "Nazwa użytkownika jest wymagana")
  @Size(min = 3, max = 50, message = "Nazwa użytkownika musi mieć od 3 do 50 znaków")
  @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Nazwa użytkownika może zawierać tylko litery, cyfry i podkreślnik")
  private String username;

  @NotBlank(message = "Email jest wymagany")
  @Email(message = "Podaj prawidłowy adres email")
  @Size(max = 100, message = "Email nie może być dłuższy niż 100 znaków")
  private String email;

  @NotBlank(message = "Hasło jest wymagane")
  @Size(min = 6, max = 100, message = "Hasło musi mieć od 6 do 100 znaków")
  private String password;

  @NotBlank(message = "Potwierdzenie hasła jest wymagane")
  private String confirmPassword;

  @Size(max = 50, message = "Imię nie może być dłuższe niż 50 znaków")
  private String firstName;

  @Size(max = 50, message = "Nazwisko nie może być dłuższe niż 50 znaków")
  private String lastName;

  public boolean isPasswordMatching() {
    return password != null && password.equals(confirmPassword);
  }
}
