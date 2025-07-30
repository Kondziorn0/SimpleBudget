package com.kondziorno.simplebudget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import com.kondziorno.simplebudget.dto.ExpenseRequest;
import com.kondziorno.simplebudget.service.ExpenseCategoryService;
import com.kondziorno.simplebudget.service.ExpenseService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/expense")
public class ExpenseController {
  private final ExpenseService expenseService;
  private final ExpenseCategoryService expenseCategoryService;

  public ExpenseController(ExpenseService expenseService, ExpenseCategoryService expenseCategoryService) {
    this.expenseService = expenseService;
    this.expenseCategoryService = expenseCategoryService;
  }

  @GetMapping
  public String showExpense(Model model) {
    model.addAttribute("expenses", expenseService.findAll());
    return "expense/show";
  }

  @GetMapping("/new")
  public String newExpense(Model model) {
    model.addAttribute("expenseRequest", new ExpenseRequest());
    model.addAttribute("expenseCategories", expenseCategoryService.findAll());
    return "expense/new";
  }

  @PostMapping("/new")
  public String createExpense(@Valid @ModelAttribute ExpenseRequest expenseRequest,
      BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("expenseCategories", expenseCategoryService.findAll());
      return "expense/new";
    }

    expenseService.save(expenseRequest);

    return "redirect:/expense";
  }
}
