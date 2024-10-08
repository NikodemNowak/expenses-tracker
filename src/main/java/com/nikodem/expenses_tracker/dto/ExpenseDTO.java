package com.nikodem.expenses_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ExpenseDTO {
    private UUID expenseId;
    private String name;
    private String description;
    private BigDecimal amount;
    private String category;
    private String expenseDate;
    private UUID monthlyFinanceId;
}
