package com.musinsacoordi.backend.common.error;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String name();
    HttpStatus getHttpStatus();
    String getMessage();
    String getFormattedMessage(Object... values);
}
