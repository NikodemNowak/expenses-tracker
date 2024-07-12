package com.nikodem.expenses_tracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "User")
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractEntity{
    @NotNull
    @Column(unique = true)
    private String email;

    private String name;
}
