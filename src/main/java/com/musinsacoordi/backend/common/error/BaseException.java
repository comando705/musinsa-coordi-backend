package com.musinsacoordi.backend.common.error;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Object[] values;

    public BaseException(ErrorCode errorCode, Object... values) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.values = values;
    }

    public BaseException(ErrorCode errorCode) {
        this(errorCode, (Object[]) null);
    }

    @Override
    public String getMessage() {
        return errorCode.getFormattedMessage(values);
    }
}
