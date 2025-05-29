package com.musinsacoordi.backend.domain.category.error;

import com.musinsacoordi.backend.common.error.BaseException;

public class CategoryException extends BaseException {
    public CategoryException(CategoryErrorCode errorCode, Object... params) {
        super(errorCode, params);
    }

    public CategoryException(CategoryErrorCode errorCode) {
        super(errorCode);
    }
} 