package com.nikodem.expenses_tracker.dto;

import com.nikodem.expenses_tracker.validation.ConditionalNotEmpty;
import lombok.Getter;

import java.util.UUID;

@Getter
public class PatchExpenseCategoryDTO {
    private UUID expenseCategoryId;
    @ConditionalNotEmpty
    private String name;
}
