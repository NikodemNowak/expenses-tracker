package com.nikodem.expenses_tracker.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ExpenseCategory extends AbstractEntity{
    @Column(unique = true)
    @NotNull
    private String expenseCategory;
}
