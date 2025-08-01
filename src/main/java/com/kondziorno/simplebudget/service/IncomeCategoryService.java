package com.kondziorno.simplebudget.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.kondziorno.simplebudget.repository.IncomeCategoryRepository;
import com.kondziorno.simplebudget.dto.IncomeCategoryRequest;
import com.kondziorno.simplebudget.model.IncomeCategory;
import com.kondziorno.simplebudget.model.User;

@Service
public class IncomeCategoryService {
  private final IncomeCategoryRepository incomeCategoryRepository;

  public IncomeCategoryService(IncomeCategoryRepository incomeCategoryRepository) {
    this.incomeCategoryRepository = incomeCategoryRepository;
  }

  public void save(IncomeCategoryRequest incomeCategoryRequest) {
    User currentUser = getCurrentUser();

    IncomeCategory incomeCategory = new IncomeCategory();
    incomeCategory.setName(incomeCategoryRequest.getName());
    incomeCategory.setDescription(incomeCategoryRequest.getDescription());
    incomeCategory.setUser(currentUser);

    incomeCategoryRepository.save(incomeCategory);
  }

  public List<IncomeCategory> findAll() {
    User currentUser = getCurrentUser();
    return incomeCategoryRepository.findByUserOrderByCreatedAtDesc(currentUser);
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
