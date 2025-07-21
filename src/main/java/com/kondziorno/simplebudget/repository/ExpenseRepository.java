package com.kondziorno.simplebudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kondziorno.simplebudget.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
  // Additional query methods can be defined here if needed
}
