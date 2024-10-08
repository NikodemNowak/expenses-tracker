package com.nikodem.expenses_tracker.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class PatchMonthlyFinanceDTO {
    @NotNull
    private UUID monthlyFinanceId;
    @NotNull
    private BigDecimal income;
}
