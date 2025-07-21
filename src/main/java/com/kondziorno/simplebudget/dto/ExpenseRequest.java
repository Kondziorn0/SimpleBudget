package com.kondziorno.simplebudget.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ExpenseRequest {

  @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
  private String name;

  @NotNull(message = "Amount cannot be null")
  @DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
  private BigDecimal amount;

  @Size(max = 255, message = "Description must be at most 255 characters")
  private String description;

  @NotNull(message = "Date cannot be null")
  private LocalDate timeWhenHappened;

  @NotNull(message = "You must provide an expense category")
  private Long expenseCategoryId;
}
