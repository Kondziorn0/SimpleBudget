package com.kondziorno.simplebudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kondziorno.simplebudget.model.ExpenseCategory;
import com.kondziorno.simplebudget.model.User;

import java.util.List;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {

  List<ExpenseCategory> findByUserOrderByCreatedAtDesc(User user);

  List<ExpenseCategory> findByUser(User user);
}
