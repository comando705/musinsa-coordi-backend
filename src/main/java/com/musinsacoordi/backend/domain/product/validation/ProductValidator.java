package com.musinsacoordi.backend.domain.product.validation;

import com.musinsacoordi.backend.domain.product.Product;
import com.musinsacoordi.backend.domain.product.ProductRepository;
import com.musinsacoordi.backend.domain.product.dto.ProductRequestDto;
import com.musinsacoordi.backend.domain.product.error.ProductErrorCode;
import com.musinsacoordi.backend.domain.product.error.ProductException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductValidator {
    private final ProductRepository productRepository;

    public void validateCreate(ProductRequestDto dto) {
        validateDuplicateProduct(dto);
    }

    public void validateUpdate(Long productId, ProductRequestDto dto) {
        validateProductExists(productId);
        validateDuplicateProductExceptSelf(productId, dto);
    }

    private void validateProductExists(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND, productId);
        }
    }

    private void validateDuplicateProduct(ProductRequestDto dto) {
        if (productRepository.existsByBrandIdAndCategoryId(dto.getBrandId(), dto.getCategoryId())) {
            throw new ProductException(
                ProductErrorCode.DUPLICATE_PRODUCT,
                dto.getBrandId(),
                dto.getCategoryId()
            );
        }
    }

    private void validateDuplicateProductExceptSelf(Long productId, ProductRequestDto dto) {
        Product existingProduct = productRepository.findByBrandIdAndCategoryId(
            dto.getBrandId(), 
            dto.getCategoryId()
        ).orElse(null);

        if (existingProduct != null && !existingProduct.getId().equals(productId)) {
            throw new ProductException(
                ProductErrorCode.DUPLICATE_PRODUCT,
                dto.getBrandId(),
                dto.getCategoryId()
            );
        }
    }
} 