package com.kondziorno.simplebudget.service;

import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.kondziorno.simplebudget.repository.IncomeRepository;
import com.kondziorno.simplebudget.repository.IncomeCategoryRepository;
import com.kondziorno.simplebudget.model.Income;
import com.kondziorno.simplebudget.model.IncomeCategory;
import com.kondziorno.simplebudget.model.User;
import com.kondziorno.simplebudget.dto.IncomeRequest;

@Service
public class IncomeService {

  private final IncomeRepository incomeRepository;
  private final IncomeCategoryRepository incomeCategoryRepository;

  public IncomeService(IncomeRepository incomeRepository,
      IncomeCategoryRepository incomeCategoryRepository) {
    this.incomeRepository = incomeRepository;
    this.incomeCategoryRepository = incomeCategoryRepository;
  }

  public Income save(IncomeRequest incomeRequest) {
    User currentUser = getCurrentUser();

    // Znajdź kategorię należącą do aktualnego użytkownika
    IncomeCategory category = incomeCategoryRepository.findById(incomeRequest.getIncomeCategoryId())
        .orElseThrow(() -> new RuntimeException("Income category not found"));

    // Sprawdź czy kategoria należy do aktualnego użytkownika
    if (!category.getUser().getId().equals(currentUser.getId())) {
      throw new RuntimeException("Access denied to this income category");
    }

    Income income = new Income();
    income.setName(incomeRequest.getName());
    income.setAmount(incomeRequest.getAmount());
    income.setDescription(incomeRequest.getDescription());
    // Konwertuj LocalDate na LocalDateTime (początek dnia)
    income.setTimeWhenHappened(incomeRequest.getTimeWhenHappened().atStartOfDay());
    income.setIncomeCategory(category);
    income.setUser(currentUser);

    return incomeRepository.save(income);
  }

  public List<Income> findAll() {
    User currentUser = getCurrentUser();
    return incomeRepository.findByUserOrderByCreatedAtDesc(currentUser);
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