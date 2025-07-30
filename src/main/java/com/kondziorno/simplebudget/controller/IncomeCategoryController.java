package com.kondziorno.simplebudget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.kondziorno.simplebudget.dto.IncomeCategoryRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import com.kondziorno.simplebudget.service.IncomeCategoryService;

@Controller
@RequestMapping("/income-categories")
public class IncomeCategoryController {
  private final IncomeCategoryService incomeCategoryService;

  public IncomeCategoryController(IncomeCategoryService incomeCategoryService) {
    this.incomeCategoryService = incomeCategoryService;
  }

  @GetMapping
  public String showIncomeCategories(Model model) {
    model.addAttribute("incomeCategories", incomeCategoryService.findAll());
    return "incomeCategory/show";
  }

  @GetMapping("/new")
  public String newIncomeCategory(Model model) {
    model.addAttribute("incomeCategoryRequest", new IncomeCategoryRequest());
    return "incomeCategory/new";
  }

  @PostMapping("/new")
  public String createIncomeCategory(@Valid @ModelAttribute IncomeCategoryRequest incomeCategoryRequest,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "incomeCategory/new";
    }

    incomeCategoryService.save(incomeCategoryRequest);

    return "redirect:/income-categories";
  }
}
