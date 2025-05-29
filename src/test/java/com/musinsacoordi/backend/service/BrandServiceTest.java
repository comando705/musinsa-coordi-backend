package com.musinsacoordi.backend.service;


import com.musinsacoordi.backend.domain.brand.Brand;
import com.musinsacoordi.backend.domain.brand.BrandRepository;
import com.musinsacoordi.backend.domain.brand.BrandService;
import com.musinsacoordi.backend.domain.brand.dto.BrandRequestDto;
import com.musinsacoordi.backend.domain.brand.dto.BrandResponseDto;
import com.musinsacoordi.backend.domain.brand.error.BrandErrorCode; //
import com.musinsacoordi.backend.domain.brand.error.BrandException; //
import com.musinsacoordi.backend.domain.brand.validation.BrandValidator; //
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTest {

    @Mock // Mock 객체 주입
    private BrandRepository brandRepository;

    @Mock // Mock 객체 주입
    private BrandValidator brandValidator;

    @InjectMocks // Mock 객체들을 주입받을 대상 서비스
    private BrandService brandService;

    private Brand brandA;

    @BeforeEach
    void setUp() {
        brandA = Brand.builder()
                .name("BrandA")
                .build();
        
        // ID 설정
        try {
            java.lang.reflect.Field idField = Brand.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(brandA, 1L);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("브랜드 생성 성공")
    void createBrand_ValidInput_Success() {
        // Given
        BrandRequestDto requestDto = BrandRequestDto.builder()
                .name("NewBrand")
                .build();

        Brand newBrand = Brand.builder()
                .name(requestDto.getName())
                .build();
        
        // ID 설정
        try {
            java.lang.reflect.Field idField = Brand.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(newBrand, 1L);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        when(brandRepository.save(any(Brand.class))).thenReturn(newBrand);

        // When
        BrandResponseDto responseDto = brandService.createBrand(requestDto);

        // Then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getName()).isEqualTo("NewBrand");
        assertThat(responseDto.getId()).isPositive();
        verify(brandValidator, times(1)).validateCreate(requestDto);
        verify(brandRepository, times(1)).save(any(Brand.class));
    }

    @Test
    @DisplayName("브랜드 생성 실패: 중복 이름")
    void createBrand_DuplicateName_ThrowsException() {
        // Given
        BrandRequestDto requestDto = BrandRequestDto.builder()
                .name("ExistingBrand")
                .build();

        // BrandValidator가 중복 이름에 대해 예외를 던지도록 설정
        doThrow(new BrandException(BrandErrorCode.DUPLICATE_BRAND_NAME, "ExistingBrand")) //
                .when(brandValidator).validateCreate(requestDto); //

        // When & Then
        assertThatThrownBy(() -> brandService.createBrand(requestDto)) //
                .isInstanceOf(BrandException.class) //
                .hasMessageContaining("이미 존재하는 브랜드 이름입니다: ExistingBrand"); //

        verify(brandValidator, times(1)).validateCreate(requestDto); //
        verify(brandRepository, never()).save(any(Brand.class)); //
    }

    @Test
    @DisplayName("모든 브랜드 조회 성공")
    void getAllBrands_Success() {
        // Given
        Brand brandB = Brand.builder().name("BrandB").build(); //
        List<Brand> brands = Arrays.asList(brandA, brandB);
        when(brandRepository.findAll()).thenReturn(brands); //

        // When
        List<BrandResponseDto> responseDtos = brandService.getAllBrands(); //

        // Then
        assertThat(responseDtos).hasSize(2);
        assertThat(responseDtos.get(0).getName()).isEqualTo("BrandA");
        assertThat(responseDtos.get(1).getName()).isEqualTo("BrandB");
        verify(brandRepository, times(1)).findAll(); //
    }

    @Test
    @DisplayName("ID로 브랜드 조회 성공")
    void getBrandById_Success() {
        // Given
        Long brandId = 1L;
        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brandA)); //

        // When
        BrandResponseDto responseDto = brandService.getBrandById(brandId); //

        // Then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getName()).isEqualTo("BrandA");
        verify(brandRepository, times(1)).findById(brandId); //
    }

    @Test
    @DisplayName("ID로 브랜드 조회 실패: 브랜드를 찾을 수 없음")
    void getBrandById_NotFound_ThrowsException() {
        // Given
        Long brandId = 99L;
        when(brandRepository.findById(brandId)).thenReturn(Optional.empty()); //

        // When & Then
        assertThatThrownBy(() -> brandService.getBrandById(brandId)) //
                .isInstanceOf(BrandException.class) //
                .hasMessageContaining("브랜드를 찾을 수 없습니다. ID: " + brandId); //

        verify(brandRepository, times(1)).findById(brandId); //
    }

    @Test
    @DisplayName("브랜드 수정 성공")
    void updateBrand_ValidInput_Success() {
        // Given
        Long brandId = 1L;
        BrandRequestDto requestDto = BrandRequestDto.builder()
                .name("UpdatedBrandName")
                .build();

        // 기존 브랜드 객체 Mocking
        Brand existingBrand = Brand.builder()
                .name("OriginalBrand")
                .build();
        try {
            java.lang.reflect.Field idField = Brand.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(existingBrand, brandId);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        when(brandRepository.findById(brandId)).thenReturn(Optional.of(existingBrand));
        doNothing().when(brandValidator).validateUpdate(brandId, requestDto);

        // When
        BrandResponseDto responseDto = brandService.updateBrand(brandId, requestDto);

        // Then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getName()).isEqualTo("UpdatedBrandName");
        
        // 검증 순서 중요: findById가 먼저 호출되고, 그 다음 validateUpdate가 호출되어야 함
        verify(brandRepository, times(1)).findById(brandId);
        verify(brandValidator, times(1)).validateUpdate(brandId, requestDto);
        assertThat(existingBrand.getName()).isEqualTo("UpdatedBrandName");
    }

    @Test
    @DisplayName("브랜드 수정 실패: 중복 이름")
    void updateBrand_DuplicateName_ThrowsException() {
        // Given
        Long brandId = 1L;
        BrandRequestDto requestDto = BrandRequestDto.builder()
                .name("DuplicateName")
                .build();

        Brand existingBrand = Brand.builder()
                .name("OriginalName")
                .build();
        try {
            java.lang.reflect.Field idField = Brand.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(existingBrand, brandId);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        // BrandValidator가 중복 이름에 대해 예외를 던지도록 설정
        doThrow(new BrandException(BrandErrorCode.DUPLICATE_BRAND_NAME, "DuplicateName"))
                .when(brandValidator).validateUpdate(eq(brandId), any(BrandRequestDto.class));

        // When & Then
        assertThatThrownBy(() -> brandService.updateBrand(brandId, requestDto))
                .isInstanceOf(BrandException.class)
                .hasMessageContaining("이미 존재하는 브랜드 이름입니다: DuplicateName");

        // 검증 순서 중요: validateUpdate가 먼저 호출되고 예외가 발생하므로, findById는 호출되지 않아야 함
        verify(brandValidator).validateUpdate(eq(brandId), any(BrandRequestDto.class));
        verify(brandRepository, never()).findById(anyLong());
    }

    @Test
    @DisplayName("브랜드 수정 실패: 브랜드를 찾을 수 없음")
    void updateBrand_NotFound_ThrowsException() {
        // Given
        Long brandId = 99L;
        BrandRequestDto requestDto = BrandRequestDto.builder()
                .name("AnyName")
                .build();

        // BrandValidator가 브랜드를 찾을 수 없다는 예외를 던지도록 설정
        doThrow(new BrandException(BrandErrorCode.BRAND_NOT_FOUND, brandId))
                .when(brandValidator).validateUpdate(eq(brandId), any(BrandRequestDto.class));

        // When & Then
        assertThatThrownBy(() -> brandService.updateBrand(brandId, requestDto))
                .isInstanceOf(BrandException.class)
                .hasMessageContaining("브랜드를 찾을 수 없습니다. ID: " + brandId);

        // 검증 순서 중요: validateUpdate가 먼저 호출되고 예외가 발생하므로, findById는 호출되지 않아야 함
        verify(brandValidator).validateUpdate(eq(brandId), any(BrandRequestDto.class));
        verify(brandRepository, never()).findById(anyLong());
    }

    @Test
    @DisplayName("브랜드 삭제 성공")
    void deleteBrand_Success() {
        // Given
        Long brandId = 1L;
        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brandA)); //
        doNothing().when(brandRepository).delete(brandA); //

        // When
        brandService.deleteBrand(brandId); //

        // Then
        verify(brandRepository, times(1)).findById(brandId); //
        verify(brandRepository, times(1)).delete(brandA); //
    }

    @Test
    @DisplayName("브랜드 삭제 실패: 브랜드를 찾을 수 없음")
    void deleteBrand_NotFound_ThrowsException() {
        // Given
        Long brandId = 99L;
        when(brandRepository.findById(brandId)).thenReturn(Optional.empty()); //

        // When & Then
        assertThatThrownBy(() -> brandService.deleteBrand(brandId)) //
                .isInstanceOf(BrandException.class) //
                .hasMessageContaining("브랜드를 찾을 수 없습니다. ID: " + brandId); //

        verify(brandRepository, times(1)).findById(brandId); //
        verify(brandRepository, never()).delete(any(Brand.class)); //
    }
}