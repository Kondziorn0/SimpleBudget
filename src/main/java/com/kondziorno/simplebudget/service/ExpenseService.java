package com.kondziorno.simplebudget.service;

import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.kondziorno.simplebudget.repository.ExpenseRepository;
import com.kondziorno.simplebudget.repository.ExpenseCategoryRepository;
import com.kondziorno.simplebudget.model.Expense;
import com.kondziorno.simplebudget.model.ExpenseCategory;
import com.kondziorno.simplebudget.model.User;
import com.kondziorno.simplebudget.dto.ExpenseRequest;

@Service
public class ExpenseService {
  private final ExpenseRepository expenseRepository;
  private final ExpenseCategoryRepository expenseCategoryRepository;

  public ExpenseService(ExpenseRepository expenseRepository,
      ExpenseCategoryRepository expenseCategoryRepository) {
    this.expenseRepository = expenseRepository;
    this.expenseCategoryRepository = expenseCategoryRepository;
  }

  public Expense save(ExpenseRequest expenseRequest) {
    User currentUser = getCurrentUser();

    // Znajdź kategorię należącą do aktualnego użytkownika
    ExpenseCategory category = expenseCategoryRepository.findById(expenseRequest.getExpenseCategoryId())
        .orElseThrow(() -> new RuntimeException("Expense category not found"));

    // Sprawdź czy kategoria należy do aktualnego użytkownika
    if (!category.getUser().getId().equals(currentUser.getId())) {
      throw new RuntimeException("Access denied to this expense category");
    }

    Expense expense = new Expense();
    expense.setName(expenseRequest.getName());
    expense.setAmount(expenseRequest.getAmount());
    expense.setDescription(expenseRequest.getDescription());
    // Konwertuj LocalDate na LocalDateTime (początek dnia)
    expense.setTimeWhenHappened(expenseRequest.getTimeWhenHappened().atStartOfDay());
    expense.setExpenseCategory(category);
    expense.setUser(currentUser);

    return expenseRepository.save(expense);
  }

  public List<Expense> findAll() {
    User currentUser = getCurrentUser();
    return expenseRepository.findByUserOrderByCreatedAtDesc(currentUser);
  }

  private User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated() ||
        authentication.getPrincipal().equals("anonymousUser")) {
      throw new RuntimeException("User not authenticated");
    }
    return (User) authentication.getPrincipal();
  }
}
