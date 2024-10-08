package com.nikodem.expenses_tracker.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;

@Getter
public class PostExpenseDTO {
    @NotNull
    @NotBlank
    private String name;
    private String description;
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 7, fraction = 2)
    private BigDecimal amount;
    @NotNull
    @NotBlank
    private String categoryName;
    @NotNull
    @NotBlank
    private String expenseDate;
    @NotNull
    @UUID
    private String userId;
}
