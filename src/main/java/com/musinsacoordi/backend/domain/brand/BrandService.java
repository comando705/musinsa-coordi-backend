package com.musinsacoordi.backend.domain.brand;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.musinsacoordi.backend.common.error.BaseException;
import com.musinsacoordi.backend.common.error.ErrorCode;
import com.musinsacoordi.backend.domain.brand.dto.BrandRequestDto;
import com.musinsacoordi.backend.domain.brand.dto.BrandResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BrandService {

    private final BrandRepository brandRepository;

    /**
     * 브랜드 생성
     */
    @Transactional
    public BrandResponseDto createBrand(BrandRequestDto requestDto) {
        // 이름 중복 검사
        if (brandRepository.findByName(requestDto.getName()).isPresent()) {
            throw new BaseException(ErrorCode.ALREADY_EXIST_ENTITY, "브랜드", requestDto.getName());
        }
        
        Brand brand = requestDto.toEntity();
        Brand savedBrand = brandRepository.save(brand);
        return BrandResponseDto.of(savedBrand);
    }

    /**
     * 브랜드 전체 조회
     */
    public List<BrandResponseDto> getAllBrands() {
        return brandRepository.findAll().stream()
                .map(BrandResponseDto::of)
                .collect(Collectors.toList());
    }

    /**
     * 브랜드 단일 조회
     */
    public BrandResponseDto getBrandById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND, "브랜드", id));
        return BrandResponseDto.of(brand);
    }

    /**
     * 브랜드 수정
     */
    @Transactional
    public BrandResponseDto updateBrand(Long id, BrandRequestDto requestDto) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND, "브랜드", id));
        
        // 이름 중복 검사 (자기 자신 제외)
        brandRepository.findByName(requestDto.getName())
                .ifPresent(existingBrand -> {
                    if (!existingBrand.getId().equals(id)) {
                        throw new BaseException(ErrorCode.ALREADY_EXIST_ENTITY, "브랜드", requestDto.getName());
                    }
                });
        
        brand.updateName(requestDto.getName());
        return BrandResponseDto.of(brand);
    }

    /**
     * 브랜드 삭제
     */
    @Transactional
    public void deleteBrand(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND, "브랜드", id));
        brandRepository.delete(brand);
    }
}