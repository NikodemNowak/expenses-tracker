package com.nikodem.expenses_tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Expense")
@Data
@EqualsAndHashCode(callSuper = true)
public class Expense extends AbstractEntity {
    @NotNull
    private String name;

    private String description;

    @NotNull
    private BigDecimal amount;

    @ManyToOne
    private ExpenseCategory category;

    private LocalDate expenseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private MonthlyFinance monthlyFinance;
}
