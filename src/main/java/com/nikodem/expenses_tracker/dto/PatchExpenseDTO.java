package com.nikodem.expenses_tracker.dto;

import com.nikodem.expenses_tracker.validation.ConditionalNotEmpty;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class PatchExpenseDTO {
    private UUID expenseId;
    @ConditionalNotEmpty
    private String name;
    @ConditionalNotEmpty
    private String description;
    private BigDecimal amount;
    @ConditionalNotEmpty
    private String categoryName;
    @ConditionalNotEmpty
    private String expenseDate;
    @ConditionalNotEmpty
    private String monthlyFinanceId;
    // TODO: delete and change monthly finance based on expense date
}
