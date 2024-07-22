package com.nikodem.expenses_tracker.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class PostExpenseDTO {
    @NotBlank
    private String name;
    private String description;
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 7, fraction = 2)
    private BigDecimal amount;
    @NotBlank
    private String categoryName;
    @NotBlank
    private String expenseDate;
    @NotBlank
    private String monthlyFinanceId;
    // TODO: change it to automatic assign of monthly finance based on expense date
}
