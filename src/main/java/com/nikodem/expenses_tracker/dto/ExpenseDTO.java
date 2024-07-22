package com.nikodem.expenses_tracker.dto;

import com.nikodem.expenses_tracker.model.ExpenseCategory;
import com.nikodem.expenses_tracker.model.MonthlyFinance;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ExpenseDTO {
    private UUID expenseId;
    private String name;
    private String description;
    private BigDecimal amount;
    private ExpenseCategory category;
    private LocalDate expenseDate;
    private MonthlyFinance monthlyFinance;
}
