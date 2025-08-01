package com.kondziorno.simplebudget.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/")
  public String index(Authentication authentication, Model model) {
    if (authentication != null && authentication.isAuthenticated() &&
        !authentication.getName().equals("anonymousUser")) {
      model.addAttribute("isAuthenticated", true);
      model.addAttribute("username", authentication.getName());
    } else {
      model.addAttribute("isAuthenticated", false);
    }
    return "home/index";
  }
}
