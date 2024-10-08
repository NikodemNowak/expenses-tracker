package com.nikodem.expenses_tracker.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "expensecategories")
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseCategory extends AbstractEntity{
    @Column(unique = true)
    @NotNull
    private String name;
}
