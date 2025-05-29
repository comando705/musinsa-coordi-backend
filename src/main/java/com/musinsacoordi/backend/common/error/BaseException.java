package com.musinsacoordi.backend.common.error;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Object[] params;

    public BaseException(ErrorCode errorCode, Object... params) {
        super(errorCode.getFormattedMessage(params));
        this.errorCode = errorCode;
        this.params = params;
    }

    public BaseException(ErrorCode errorCode) {
        this(errorCode, (Object[]) null);
    }
}
