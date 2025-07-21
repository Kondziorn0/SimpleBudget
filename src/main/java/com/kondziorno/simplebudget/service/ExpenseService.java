package com.kondziorno.simplebudget.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.kondziorno.simplebudget.repository.ExpenseRepository;
import com.kondziorno.simplebudget.repository.ExpenseCategoryRepository;
import com.kondziorno.simplebudget.model.Expense;
import com.kondziorno.simplebudget.model.ExpenseCategory;
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
    // Znajdź kategorię
    ExpenseCategory category = expenseCategoryRepository.findById(expenseRequest.getExpenseCategoryId())
        .orElseThrow(() -> new RuntimeException("Expense category not found"));
    
    Expense expense = new Expense();
    expense.setName(expenseRequest.getName());
    expense.setAmount(expenseRequest.getAmount());
    expense.setDescription(expenseRequest.getDescription());
    // Konwertuj LocalDate na LocalDateTime (początek dnia)
    expense.setTimeWhenHappened(expenseRequest.getTimeWhenHappened().atStartOfDay());
    expense.setExpenseCategory(category);
    
    return expenseRepository.save(expense);
  }
  
  public List<Expense> findAll() {
    return expenseRepository.findAll();
  }
}
