package com.kondziorno.simplebudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kondziorno.simplebudget.model.ExpenseCategory;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {
  // Additional query methods can be defined here if needed
}
