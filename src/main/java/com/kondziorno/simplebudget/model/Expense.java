package com.kondziorno.simplebudget.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
public class Expense {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  @Column(name = "amount", precision = 10, scale = 2)
  private BigDecimal amount;

  private String description;
  private LocalDateTime timeWhenHappened;

  @ManyToOne
  @JoinColumn(name = "expense_category_id", nullable = false)
  private ExpenseCategory expenseCategory;

  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @PrePersist
  protected void onCreate() {
      createdAt = LocalDateTime.now();
      updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
      updatedAt = LocalDateTime.now();
  }
  // Getters and Setters
}
