package com.kondziorno.simplebudget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.kondziorno.simplebudget.dto.ExpenseCategoryRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import com.kondziorno.simplebudget.service.ExpenseCategoryService;

@Controller
@RequestMapping("/expense-categories")
public class ExpenseCategoryController {
  private final ExpenseCategoryService expenseCategoryService;

  public ExpenseCategoryController(ExpenseCategoryService expenseCategoryService) {
    this.expenseCategoryService = expenseCategoryService;
  }

  @GetMapping
  public String showExpenseCategories(Model model) {
    model.addAttribute("expenseCategories", expenseCategoryService.findAll());
    return "expenseCategory/show";
  }

  @GetMapping("/new")
  public String newExpenseCategory(Model model) {
    model.addAttribute("expenseCategoryRequest", new ExpenseCategoryRequest());
    return "expenseCategory/new";
  }

  @PostMapping("/new")
  public String createExpenseCategory(@Valid @ModelAttribute ExpenseCategoryRequest expenseCategoryRequest,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "expenseCategory/new";
    }

    expenseCategoryService.save(expenseCategoryRequest);

    return "redirect:/expense-categories";
  }
}
