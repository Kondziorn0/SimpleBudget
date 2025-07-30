package com.kondziorno.simplebudget.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.kondziorno.simplebudget.repository.ExpenseCategoryRepository;
import com.kondziorno.simplebudget.dto.ExpenseCategoryRequest;
import com.kondziorno.simplebudget.model.ExpenseCategory;

@Service
public class ExpenseCategoryService {

  private final ExpenseCategoryRepository expenseCategoryRepository;

  public ExpenseCategoryService(ExpenseCategoryRepository expenseCategoryRepository) {
    this.expenseCategoryRepository = expenseCategoryRepository;
  }

  public void save(ExpenseCategoryRequest expenseCategoryRequest) {
    ExpenseCategory expenseCategory = new ExpenseCategory();
    expenseCategory.setName(expenseCategoryRequest.getName());
    expenseCategory.setDescription(expenseCategoryRequest.getDescription());

    expenseCategoryRepository.save(expenseCategory);
  }

  public List<ExpenseCategory> findAll() {
    return expenseCategoryRepository.findAll();
  }
}
