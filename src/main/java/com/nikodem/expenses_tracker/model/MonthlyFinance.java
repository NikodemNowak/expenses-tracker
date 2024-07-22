package com.nikodem.expenses_tracker.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MonthlyFinance extends AbstractEntity {
    @NotNull
    @ManyToOne
    @JoinColumn(name = "AppUser")
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Month month;

    private int year;

    private BigDecimal income;

    @OneToMany(mappedBy = "monthlyFinance", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Expense> expenses = new ArrayList<>();

    private BigDecimal expensesTotal;

    public MonthlyFinance(User user, Month month, int year, BigDecimal income) {
        this.user = user;
        this.month = month;
        this.year = year;
        this.income = income;
    }
}
