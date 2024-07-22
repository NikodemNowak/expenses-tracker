package com.nikodem.expenses_tracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostUserDTO {
    @NotBlank
    @Email(regexp = ".+@.+\\..+")
    private String email;
    @NotBlank
    private String name;
}
