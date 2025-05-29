package com.musinsacoordi.backend.common.error;

import com.musinsacoordi.backend.domain.brand.error.BrandErrorCode;
import com.musinsacoordi.backend.domain.brand.error.BrandException;
import com.musinsacoordi.backend.domain.category.error.CategoryErrorCode;
import com.musinsacoordi.backend.domain.category.error.CategoryException;
import com.musinsacoordi.backend.domain.product.error.ProductErrorCode;
import com.musinsacoordi.backend.domain.product.error.ProductException;
import org.springframework.stereotype.Component;

@Component
public class ErrorHandlingStrategy {
    
    public BaseException handleError(ErrorCode errorCode, DomainType domain, Object... params) {
        if (!(errorCode instanceof ErrorCode)) {
            throw new IllegalArgumentException("Invalid error code type");
        }

        return switch (domain) {
            case CATEGORY -> {
                if (!(errorCode instanceof CategoryErrorCode)) {
                    throw new IllegalArgumentException("Invalid error code type for CATEGORY domain");
                }
                yield new CategoryException((CategoryErrorCode) errorCode, params);
            }
            case PRODUCT -> {
                if (!(errorCode instanceof ProductErrorCode)) {
                    throw new IllegalArgumentException("Invalid error code type for PRODUCT domain");
                }
                yield new ProductException((ProductErrorCode) errorCode, params);
            }
            case BRAND -> {
                if (!(errorCode instanceof BrandErrorCode)) {
                    throw new IllegalArgumentException("Invalid error code type for BRAND domain");
                }
                yield new BrandException((BrandErrorCode) errorCode, params);
            }
            case COMMON -> new BaseException(errorCode, params);
        };
    }

    public BaseException handleCommonError(CommonErrorCode errorCode, Object... params) {
        return new BaseException(errorCode, params);
    }
} 