package com.musinsacoordi.backend.domain.category.error;

import com.musinsacoordi.backend.common.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CategoryErrorCode implements ErrorCode {
    // 400 BAD_REQUEST
    INVALID_CATEGORY_NAME(HttpStatus.BAD_REQUEST, "카테고리 이름이 유효하지 않습니다."),
    
    // 404 NOT_FOUND
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다. ID: %s"),
    
    // 409 CONFLICT
    DUPLICATE_CATEGORY_NAME(HttpStatus.CONFLICT, "이미 존재하는 카테고리 이름입니다: %s"),

    // 도메인 특화 에러
    CATEGORY_DEPTH_EXCEEDED(HttpStatus.BAD_REQUEST, "카테고리 깊이 제한을 초과했습니다: %s"),
    INVALID_PARENT_CATEGORY(HttpStatus.BAD_REQUEST, "유효하지 않은 상위 카테고리입니다: %s"),
    CIRCULAR_REFERENCE(HttpStatus.BAD_REQUEST, "순환 참조가 발생했습니다: %s"),
    CHILD_CATEGORIES_EXIST(HttpStatus.BAD_REQUEST, "하위 카테고리가 존재하여 삭제할 수 없습니다: %s"),
    PRODUCTS_EXIST(HttpStatus.BAD_REQUEST, "카테고리에 속한 상품이 존재하여 삭제할 수 없습니다: %s");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String getFormattedMessage(Object... values) {
        return values == null || values.length == 0 ? message : String.format(message, values);
    }
} 