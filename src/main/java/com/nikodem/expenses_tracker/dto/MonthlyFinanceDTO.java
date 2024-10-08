package com.nikodem.expenses_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class MonthlyFinanceDTO {
    private UUID monthlyFinanceId;
    private UUID userId;
    private String month;
    private int year;
    private BigDecimal income;
    private BigDecimal expensesTotal;
}
