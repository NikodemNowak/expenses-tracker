package com.nikodem.expenses_tracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostUserDTO {
    @NotNull
    @NotBlank
    @Email(regexp = ".+@.+\\..+")
    private String email;
    @NotNull
    @NotBlank
    private String name;
}
