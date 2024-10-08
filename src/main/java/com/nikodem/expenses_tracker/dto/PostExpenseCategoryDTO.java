package com.nikodem.expenses_tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PostExpenseCategoryDTO {
    @NotNull
    @NotBlank
    private String name;
}
