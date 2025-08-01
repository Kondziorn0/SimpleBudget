package com.kondziorno.simplebudget.service;

import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.kondziorno.simplebudget.repository.ExpenseCategoryRepository;
import com.kondziorno.simplebudget.dto.ExpenseCategoryRequest;
import com.kondziorno.simplebudget.model.ExpenseCategory;
import com.kondziorno.simplebudget.model.User;

@Service
public class ExpenseCategoryService {

  private final ExpenseCategoryRepository expenseCategoryRepository;

  public ExpenseCategoryService(ExpenseCategoryRepository expenseCategoryRepository) {
    this.expenseCategoryRepository = expenseCategoryRepository;
  }

  public void save(ExpenseCategoryRequest expenseCategoryRequest) {
    User currentUser = getCurrentUser();

    ExpenseCategory expenseCategory = new ExpenseCategory();
    expenseCategory.setName(expenseCategoryRequest.getName());
    expenseCategory.setDescription(expenseCategoryRequest.getDescription());
    expenseCategory.setUser(currentUser);

    expenseCategoryRepository.save(expenseCategory);
  }

  public List<ExpenseCategory> findAll() {
    User currentUser = getCurrentUser();
    return expenseCategoryRepository.findByUserOrderByCreatedAtDesc(currentUser);
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
