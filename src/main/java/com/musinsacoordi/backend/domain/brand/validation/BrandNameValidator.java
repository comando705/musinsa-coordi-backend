package com.musinsacoordi.backend.domain.brand.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BrandNameValidator implements ConstraintValidator<ValidBrandName, String> {
    private int minLength;
    private int maxLength;

    @Override
    public void initialize(ValidBrandName constraintAnnotation) {
        this.minLength = constraintAnnotation.minLength();
        this.maxLength = constraintAnnotation.maxLength();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        
        int length = value.trim().length();
        return length >= minLength && length <= maxLength;
    }
} 