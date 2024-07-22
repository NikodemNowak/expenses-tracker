package com.nikodem.expenses_tracker.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PatchUserDTO {
    private UUID userId;
    private String email;
    private String name;
}
