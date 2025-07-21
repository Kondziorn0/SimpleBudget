package com.kondziorno.simplebudget.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.kondziorno.simplebudget.repository.IncomeCategoryRepository;
import com.kondziorno.simplebudget.dto.IncomeCategoryRequest;
import com.kondziorno.simplebudget.model.IncomeCategory;

@Service
public class IncomeCategoryService {
  private final IncomeCategoryRepository incomeCategoryRepository;

  public IncomeCategoryService(IncomeCategoryRepository incomeCategoryRepository) {
    this.incomeCategoryRepository = incomeCategoryRepository;
  }

  public void save(IncomeCategoryRequest incomeCategoryRequest) {
    IncomeCategory incomeCategory = new IncomeCategory();
    incomeCategory.setName(incomeCategoryRequest.getName());
    incomeCategory.setDescription(incomeCategoryRequest.getDescription());

    incomeCategoryRepository.save(incomeCategory);
  }

  public List<IncomeCategory> findAll() {
    System.out.println("Finding all income categories " + incomeCategoryRepository);
    System.out.println("Finding all income categories " + incomeCategoryRepository.findAll());
    return incomeCategoryRepository.findAll();
  }
}
