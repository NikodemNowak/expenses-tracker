package com.nikodem.expenses_tracker.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Expense")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Expense extends AbstractEntity {

    @NotNull
    private String name;

    private String description;

    @NotNull
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "ExpenseCategory")
    private ExpenseCategory category;

    private LocalDate expenseDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonManagedReference
    private MonthlyFinance monthlyFinance;
}
