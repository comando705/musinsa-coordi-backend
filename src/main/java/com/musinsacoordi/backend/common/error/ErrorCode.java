package com.musinsacoordi.backend.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // 400 BAD_REQUEST: 잘못된 요청
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    // 404 NOT_FOUND: 리소스를 찾을 수 없음
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "%s를 찾을 수 없습니다. ID: %s"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다: %s"),

    // 405 METHOD_NOT_ALLOWED: 허용되지 않은 HTTP 메서드
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메서드입니다."),

    // 409 CONFLICT: 리소스 충돌
    ALREADY_EXIST_ENTITY(HttpStatus.CONFLICT, "이미 존재하는 %s입니다. [%s]"),

    // 500 INTERNAL_SERVER_ERROR: 내부 서버 오류
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public String getFormattedMessage(Object... values) {
        if (message.contains("%s") && values != null && values.length > 0) {
            return String.format(message, values);
        }
        return message;
    }
}
