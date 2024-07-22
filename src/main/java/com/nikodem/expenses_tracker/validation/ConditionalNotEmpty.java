package com.nikodem.expenses_tracker.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ConditionalNotEmptyValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConditionalNotEmpty {

    String message() default "Field must not be empty if provided";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
