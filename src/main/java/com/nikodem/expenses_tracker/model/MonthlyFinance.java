package com.nikodem.expenses_tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class MonthlyFinance extends AbstractEntity {
    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Month month;

    private int year;

    private BigDecimal income;

    @OneToMany(mappedBy = "monthlyFinance", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Expense> expenses = new ArrayList<>();

    private BigDecimal expensesTotal;
}
