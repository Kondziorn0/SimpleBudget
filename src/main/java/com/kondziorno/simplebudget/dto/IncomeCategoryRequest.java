package com.kondziorno.simplebudget.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IncomeCategoryRequest {

  @NotNull(message = "Name cannot be null")
  private String name;
  private String description;
}
