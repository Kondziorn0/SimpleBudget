package com.kondziorno.simplebudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kondziorno.simplebudget.model.IncomeCategory;
import com.kondziorno.simplebudget.model.User;

import java.util.List;

public interface IncomeCategoryRepository extends JpaRepository<IncomeCategory, Long> {

  List<IncomeCategory> findByUserOrderByCreatedAtDesc(User user);

  List<IncomeCategory> findByUser(User user);
}
