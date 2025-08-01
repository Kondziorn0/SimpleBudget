package com.kondziorno.simplebudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kondziorno.simplebudget.model.Expense;
import com.kondziorno.simplebudget.model.User;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

  List<Expense> findByUserOrderByCreatedAtDesc(User user);

  List<Expense> findByUser(User user);
}
