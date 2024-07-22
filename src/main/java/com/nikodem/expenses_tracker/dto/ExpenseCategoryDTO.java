package com.nikodem.expenses_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ExpenseCategoryDTO {
    private UUID expenseCategoryId;
    private String name;
}
