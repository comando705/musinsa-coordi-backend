package com.musinsacoordi.backend.domain.product.error;

import com.musinsacoordi.backend.common.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProductErrorCode implements ErrorCode {
    // 400 BAD_REQUEST
    INVALID_PRODUCT_PRICE(HttpStatus.BAD_REQUEST, "상품 가격이 유효하지 않습니다."),
    INVALID_PRODUCT_INFO(HttpStatus.BAD_REQUEST, "상품 정보가 유효하지 않습니다."),

    // 404 NOT_FOUND
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다. ID: %s"),

    // 409 CONFLICT
    DUPLICATE_PRODUCT(HttpStatus.CONFLICT, "이미 존재하는 상품입니다. [브랜드: %s, 카테고리: %s]");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String getFormattedMessage(Object... values) {
        return values == null || values.length == 0 ? message : String.format(message, values);
    }
} 