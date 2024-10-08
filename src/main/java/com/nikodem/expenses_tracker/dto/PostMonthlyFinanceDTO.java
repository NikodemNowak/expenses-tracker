package com.nikodem.expenses_tracker.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;

@Getter
public class PostMonthlyFinanceDTO {
    @NotNull
    @UUID
    private String userId;
    @NotNull
    @NotBlank
    private String month;
    @NotNull
    @Min(value = 1950)
    private int year;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal income;
}
