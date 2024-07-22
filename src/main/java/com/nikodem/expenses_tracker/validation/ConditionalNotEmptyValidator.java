package com.nikodem.expenses_tracker.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConditionalNotEmptyValidator implements ConstraintValidator<ConditionalNotEmpty, String> {

    @Override
    public void initialize(ConditionalNotEmpty constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true;
        }

        return !s.trim().isEmpty();
    }
}
