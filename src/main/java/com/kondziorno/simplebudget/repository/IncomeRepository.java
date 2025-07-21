package com.kondziorno.simplebudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kondziorno.simplebudget.model.Income;

public interface IncomeRepository extends JpaRepository<Income, Long> {
  // Additional query methods can be defined here if needed
}
