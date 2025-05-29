package com.musinsacoordi.backend.domain.brand.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BrandNameValidator.class)
@Documented
public @interface ValidBrandName {
    String message() default "브랜드 이름이 유효하지 않습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int minLength() default 1;
    int maxLength() default 50;
} 