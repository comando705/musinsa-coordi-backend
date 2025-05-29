package com.musinsacoordi.backend.domain.brand.validation;

import com.musinsacoordi.backend.domain.brand.Brand;
import com.musinsacoordi.backend.domain.brand.BrandRepository;
import com.musinsacoordi.backend.domain.brand.dto.BrandRequestDto;
import com.musinsacoordi.backend.domain.brand.error.BrandErrorCode;
import com.musinsacoordi.backend.domain.brand.error.BrandException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BrandValidator {
    private final BrandRepository brandRepository;

    public void validateCreate(BrandRequestDto dto) {
        validateDuplicateName(dto.getName());
    }

    public void validateUpdate(Long brandId, BrandRequestDto dto) {
        validateBrandExists(brandId);
        validateDuplicateNameExceptSelf(brandId, dto.getName());
    }

    private void validateBrandExists(Long brandId) {
        if (!brandRepository.existsById(brandId)) {
            throw new BrandException(BrandErrorCode.BRAND_NOT_FOUND, brandId);
        }
    }

    private void validateDuplicateName(String name) {
        if (brandRepository.existsByName(name)) {
            throw new BrandException(BrandErrorCode.DUPLICATE_BRAND_NAME, name);
        }
    }

    private void validateDuplicateNameExceptSelf(Long brandId, String name) {
        Brand existingBrand = brandRepository.findByName(name).orElse(null);
        if (existingBrand != null && !existingBrand.getId().equals(brandId)) {
            throw new BrandException(BrandErrorCode.DUPLICATE_BRAND_NAME, name);
        }
    }
} 