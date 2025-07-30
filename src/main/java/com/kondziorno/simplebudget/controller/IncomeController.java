package com.kondziorno.simplebudget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import com.kondziorno.simplebudget.dto.IncomeRequest;
import com.kondziorno.simplebudget.service.IncomeCategoryService;
import com.kondziorno.simplebudget.service.IncomeService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/income")
public class IncomeController {
  private final IncomeService incomeService;
  private final IncomeCategoryService incomeCategoryService;

  public IncomeController(IncomeService incomeService, IncomeCategoryService incomeCategoryService) {
    this.incomeService = incomeService;
    this.incomeCategoryService = incomeCategoryService;
  }

  @GetMapping
  public String showIncome(Model model) {
    model.addAttribute("incomes", incomeService.findAll());
    return "income/show";
  }

  @GetMapping("/new")
  public String newIncome(Model model) {
    model.addAttribute("incomeRequest", new IncomeRequest());
    model.addAttribute("incomeCategories", incomeCategoryService.findAll());
    System.out.println("Available income categories: " + incomeCategoryService.findAll());
    System.out.println("Model attributes: " + model.asMap());
    return "income/new";
  }

  @PostMapping("/new")
  public String createIncome(@Valid @ModelAttribute IncomeRequest incomeRequest,
      BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("incomeCategories", incomeCategoryService.findAll());
      return "income/new";
    }

    incomeService.save(incomeRequest);

    return "redirect:/income";
  }
}