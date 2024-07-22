package com.nikodem.expenses_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserDTO {
    private UUID userId;
    private String email;
    private String name;
}
