package com.musinsacoordi.backend.domain.brand;

import com.musinsacoordi.backend.domain.brand.dto.BrandRequestDto;
import com.musinsacoordi.backend.domain.brand.dto.BrandResponseDto;
import com.musinsacoordi.backend.domain.brand.error.BrandErrorCode;
import com.musinsacoordi.backend.domain.brand.error.BrandException;
import com.musinsacoordi.backend.domain.brand.validation.BrandValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BrandService {
    private final BrandRepository brandRepository;
    private final BrandValidator brandValidator;

    @Transactional
    public BrandResponseDto createBrand(BrandRequestDto requestDto) {
        // 비즈니스 규칙 검증
        brandValidator.validateCreate(requestDto);

        // 엔티티 생성 및 저장
        Brand brand = Brand.builder()
                .name(requestDto.getName())
                .build();

        Brand savedBrand = brandRepository.save(brand);
        return BrandResponseDto.of(savedBrand);
    }

    public List<BrandResponseDto> getAllBrands() {
        return brandRepository.findAll().stream()
                .map(BrandResponseDto::of)
                .toList();
    }

    public BrandResponseDto getBrandById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new BrandException(BrandErrorCode.BRAND_NOT_FOUND, id));
        return BrandResponseDto.of(brand);
    }

    @Transactional
    public BrandResponseDto updateBrand(Long id, BrandRequestDto requestDto) {
        // 비즈니스 규칙 검증
        brandValidator.validateUpdate(id, requestDto);

        // 엔티티 조회 및 수정
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new BrandException(BrandErrorCode.BRAND_NOT_FOUND, id));

        brand.updateName(requestDto.getName());
        return BrandResponseDto.of(brand);
    }

    @Transactional
    public void deleteBrand(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new BrandException(BrandErrorCode.BRAND_NOT_FOUND, id));
        brandRepository.delete(brand);
    }
}