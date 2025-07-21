package com.kondziorno.simplebudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kondziorno.simplebudget.model.IncomeCategory;

public interface IncomeCategoryRepository extends JpaRepository<IncomeCategory, Long> {
  // Additional query methods can be defined here if needed
}
