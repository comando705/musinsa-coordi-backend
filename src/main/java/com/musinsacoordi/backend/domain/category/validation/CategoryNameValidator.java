package com.musinsacoordi.backend.domain.category.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CategoryNameValidator implements ConstraintValidator<ValidCategoryName, String> {
    private int minLength;
    private int maxLength;

    @Override
    public void initialize(ValidCategoryName constraintAnnotation) {
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