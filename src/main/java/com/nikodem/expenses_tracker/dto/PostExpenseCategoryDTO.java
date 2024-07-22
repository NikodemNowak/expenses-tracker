package com.nikodem.expenses_tracker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostExpenseCategoryDTO {
    @NotBlank
    private String name;
}
