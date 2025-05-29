package com.musinsacoordi.backend.domain.brand.error;

import com.musinsacoordi.backend.common.error.BaseException;

public class BrandException extends BaseException {
    public BrandException(BrandErrorCode errorCode, Object... params) {
        super(errorCode, params);
    }

    public BrandException(BrandErrorCode errorCode) {
        super(errorCode);
    }
} 