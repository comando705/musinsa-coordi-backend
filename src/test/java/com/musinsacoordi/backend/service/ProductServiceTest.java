package com.musinsacoordi.backend.service;

import com.musinsacoordi.backend.domain.brand.Brand;
import com.musinsacoordi.backend.domain.brand.BrandRepository;
import com.musinsacoordi.backend.domain.category.Category;
import com.musinsacoordi.backend.domain.category.CategoryRepository;
import com.musinsacoordi.backend.domain.product.Product;
import com.musinsacoordi.backend.domain.product.ProductRepository;
import com.musinsacoordi.backend.domain.product.ProductService;
import com.musinsacoordi.backend.domain.product.dto.ProductRequestDto;
import com.musinsacoordi.backend.domain.product.dto.ProductResponseDto;
import com.musinsacoordi.backend.domain.product.error.ProductException;
import com.musinsacoordi.backend.domain.product.validation.ProductValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * ProductService 테스트
 *
 * 설계 방향 및 원칙:
 * - 서비스 계층의 단위 테스트
 * - 외부 의존성(Repository)은 Mocking하여 격리된 테스트 수행
 * - 비즈니스 로직 검증에 집중
 *
 * 기술적 고려사항:
 * - MockitoExtension을 사용한 Mocking
 * - 각 메서드별 성공/실패 케이스 검증
 * - 예외 처리 검증
 *
 * 사용 시 고려사항:
 * - 실제 DB 연동 없이 Mocking된 Repository로 테스트
 * - 각 비즈니스 로직별 성공/실패 케이스 검증
 */
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductValidator productValidator;

    @Test
    @DisplayName("상품 생성 - 성공")
    void createProduct_Success() {
        // given
        ProductRequestDto requestDto = new ProductRequestDto(1L, 1L, 10000);
        Brand brand = Brand.builder().id(1L).name("브랜드명").build();
        Category category = Category.builder().id(1L).name("카테고리명").build();
        Product product = Product.builder()
                .id(1L)
                .brand(brand)
                .category(category)
                .price(10000)
                .build();

        given(brandRepository.findById(1L)).willReturn(Optional.of(brand));
        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));
        given(productRepository.save(any(Product.class))).willReturn(product);

        // when
        ProductResponseDto responseDto = productService.createProduct(requestDto);

        // then
        assertThat(responseDto.getId()).isEqualTo(1L);
        assertThat(responseDto.getPrice()).isEqualTo(10000);
        verify(productValidator).validateCreate(requestDto);
    }

    @Test
    @DisplayName("상품 생성 - 실패 (브랜드 없음)")
    void createProduct_Fail_BrandNotFound() {
        // given
        ProductRequestDto requestDto = new ProductRequestDto(1L, 1L, 10000);
        given(brandRepository.findById(1L)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> productService.createProduct(requestDto))
                .isInstanceOf(ProductException.class);
    }

    @Test
    @DisplayName("전체 상품 조회 - 성공")
    void getAllProducts_Success() {
        // given
        Brand brand = Brand.builder().id(1L).name("브랜드명").build();
        Category category = Category.builder().id(1L).name("카테고리명").build();
        List<Product> products = List.of(
                Product.builder().id(1L).brand(brand).category(category).price(10000).build(),
                Product.builder().id(2L).brand(brand).category(category).price(20000).build()
        );
        given(productRepository.findAll()).willReturn(products);

        // when
        List<ProductResponseDto> responseDtoList = productService.getAllProducts();

        // then
        assertThat(responseDtoList).hasSize(2);
        assertThat(responseDtoList.get(0).getId()).isEqualTo(1L);
        assertThat(responseDtoList.get(1).getId()).isEqualTo(2L);
    }

    @Test
    @DisplayName("상품 단일 조회 - 성공")
    void getProductById_Success() {
        // given
        Brand brand = Brand.builder().id(1L).name("브랜드명").build();
        Category category = Category.builder().id(1L).name("카테고리명").build();
        Product product = Product.builder()
                .id(1L)
                .brand(brand)
                .category(category)
                .price(10000)
                .build();
        given(productRepository.findById(1L)).willReturn(Optional.of(product));

        // when
        ProductResponseDto responseDto = productService.getProductById(1L);

        // then
        assertThat(responseDto.getId()).isEqualTo(1L);
        assertThat(responseDto.getPrice()).isEqualTo(10000);
    }

    @Test
    @DisplayName("상품 단일 조회 - 실패 (상품 없음)")
    void getProductById_Fail_ProductNotFound() {
        // given
        given(productRepository.findById(1L)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> productService.getProductById(1L))
                .isInstanceOf(ProductException.class);
    }

    @Test
    @DisplayName("브랜드별 상품 조회 - 성공")
    void getProductsByBrandId_Success() {
        // given
        Brand brand = Brand.builder().id(1L).name("브랜드명").build();
        Category category = Category.builder().id(1L).name("카테고리명").build();
        List<Product> products = List.of(
                Product.builder().id(1L).brand(brand).category(category).price(10000).build(),
                Product.builder().id(2L).brand(brand).category(category).price(20000).build()
        );
        given(brandRepository.findById(1L)).willReturn(Optional.of(brand));
        given(productRepository.findByBrand(brand)).willReturn(products);

        // when
        List<ProductResponseDto> responseDtoList = productService.getProductsByBrandId(1L);

        // then
        assertThat(responseDtoList).hasSize(2);
        assertThat(responseDtoList.get(0).getId()).isEqualTo(1L);
        assertThat(responseDtoList.get(1).getId()).isEqualTo(2L);
    }

    @Test
    @DisplayName("카테고리별 상품 조회 - 성공")
    void getProductsByCategoryId_Success() {
        // given
        Brand brand = Brand.builder().id(1L).name("브랜드명").build();
        Category category = Category.builder().id(1L).name("카테고리명").build();
        List<Product> products = List.of(
                Product.builder().id(1L).brand(brand).category(category).price(10000).build(),
                Product.builder().id(2L).brand(brand).category(category).price(20000).build()
        );
        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));
        given(productRepository.findByCategory(category)).willReturn(products);

        // when
        List<ProductResponseDto> responseDtoList = productService.getProductsByCategoryId(1L);

        // then
        assertThat(responseDtoList).hasSize(2);
        assertThat(responseDtoList.get(0).getId()).isEqualTo(1L);
        assertThat(responseDtoList.get(1).getId()).isEqualTo(2L);
    }

    @Test
    @DisplayName("상품 수정 - 성공")
    void updateProduct_Success() {
        // given
        ProductRequestDto requestDto = new ProductRequestDto(2L, 2L, 20000);
        Brand oldBrand = Brand.builder().id(1L).name("브랜드1").build();
        Category oldCategory = Category.builder().id(1L).name("카테고리1").build();
        Brand newBrand = Brand.builder().id(2L).name("브랜드2").build();
        Category newCategory = Category.builder().id(2L).name("카테고리2").build();
        Product product = Product.builder()
                .id(1L)
                .brand(oldBrand)
                .category(oldCategory)
                .price(10000)
                .build();

        given(productRepository.findById(1L)).willReturn(Optional.of(product));
        given(brandRepository.findById(2L)).willReturn(Optional.of(newBrand));
        given(categoryRepository.findById(2L)).willReturn(Optional.of(newCategory));

        // when
        ProductResponseDto responseDto = productService.updateProduct(1L, requestDto);

        // then
        assertThat(responseDto.getId()).isEqualTo(1L);
        assertThat(responseDto.getPrice()).isEqualTo(20000);
        verify(productValidator).validateUpdate(1L, requestDto);
    }

    @Test
    @DisplayName("상품 삭제 - 성공")
    void deleteProduct_Success() {
        // given
        Brand brand = Brand.builder().id(1L).name("브랜드명").build();
        Category category = Category.builder().id(1L).name("카테고리명").build();
        Product product = Product.builder()
                .id(1L)
                .brand(brand)
                .category(category)
                .price(10000)
                .build();
        given(productRepository.findById(1L)).willReturn(Optional.of(product));

        // when
        productService.deleteProduct(1L);

        // then
        verify(productRepository).delete(product);
    }
} 