package com.kondziorno.simplebudget.controller;

import com.kondziorno.simplebudget.dto.UserRegistrationDto;
import com.kondziorno.simplebudget.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

  private final UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/login")
  public String loginPage(@RequestParam(value = "error", required = false) String error,
      @RequestParam(value = "logout", required = false) String logout,
      Model model) {
    if (error != null) {
      model.addAttribute("errorMessage", "Nieprawidłowa nazwa użytkownika lub hasło");
    }
    if (logout != null) {
      model.addAttribute("successMessage", "Zostałeś pomyślnie wylogowany");
    }
    return "auth/login";
  }

  @GetMapping("/register")
  public String registerPage(Model model) {
    model.addAttribute("userRegistrationDto", new UserRegistrationDto());
    return "auth/register";
  }

  @PostMapping("/register")
  public String registerUser(@Valid @ModelAttribute UserRegistrationDto userRegistrationDto,
      BindingResult bindingResult,
      Model model,
      RedirectAttributes redirectAttributes) {

    // Sprawdź czy użytkownik już istnieje
    if (userService.existsByUsername(userRegistrationDto.getUsername())) {
      bindingResult.rejectValue("username", "error.username", "Nazwa użytkownika już istnieje");
    }

    if (userService.existsByEmail(userRegistrationDto.getEmail())) {
      bindingResult.rejectValue("email", "error.email", "Email już jest używany");
    }

    if (!userRegistrationDto.isPasswordMatching()) {
      bindingResult.rejectValue("confirmPassword", "error.password", "Hasła nie są identyczne");
    }

    if (bindingResult.hasErrors()) {
      return "auth/register";
    }

    try {
      userService.registerUser(userRegistrationDto);
      redirectAttributes.addFlashAttribute("successMessage",
          "Rejestracja przebiegła pomyślnie! Możesz się teraz zalogować.");
      return "redirect:/login";
    } catch (Exception e) {
      model.addAttribute("errorMessage", "Wystąpił błąd podczas rejestracji: " + e.getMessage());
      return "auth/register";
    }
  }
}
