package com.musinsacoordi.backend.domain.product.error;

import com.musinsacoordi.backend.common.error.BaseException;

public class ProductException extends BaseException {
    public ProductException(ProductErrorCode errorCode, Object... params) {
        super(errorCode, params);
    }

    public ProductException(ProductErrorCode errorCode) {
        super(errorCode);
    }
} 