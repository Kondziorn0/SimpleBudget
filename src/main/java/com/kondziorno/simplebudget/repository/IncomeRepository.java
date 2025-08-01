package com.kondziorno.simplebudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kondziorno.simplebudget.model.Income;
import com.kondziorno.simplebudget.model.User;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {

  List<Income> findByUserOrderByCreatedAtDesc(User user);

  List<Income> findByUser(User user);
}
