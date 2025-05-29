package com.musinsacoordi.backend.domain.brand.error;

import com.musinsacoordi.backend.common.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BrandErrorCode implements ErrorCode {
    // 400 BAD_REQUEST
    INVALID_BRAND_NAME(HttpStatus.BAD_REQUEST, "브랜드 이름이 유효하지 않습니다."),
    
    // 404 NOT_FOUND
    BRAND_NOT_FOUND(HttpStatus.NOT_FOUND, "브랜드를 찾을 수 없습니다. ID: %s"),
    
    // 409 CONFLICT
    DUPLICATE_BRAND_NAME(HttpStatus.CONFLICT, "이미 존재하는 브랜드 이름입니다: %s");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String getFormattedMessage(Object... values) {
        return values == null || values.length == 0 ? message : String.format(message, values);
    }
} 