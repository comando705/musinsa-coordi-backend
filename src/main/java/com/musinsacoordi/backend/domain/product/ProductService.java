package com.musinsacoordi.backend.domain.product;

import com.musinsacoordi.backend.domain.brand.Brand;
import com.musinsacoordi.backend.domain.brand.BrandRepository;
import com.musinsacoordi.backend.domain.category.Category;
import com.musinsacoordi.backend.domain.category.CategoryRepository;
import com.musinsacoordi.backend.domain.product.dto.ProductRequestDto;
import com.musinsacoordi.backend.domain.product.dto.ProductResponseDto;
import com.musinsacoordi.backend.domain.product.error.ProductErrorCode;
import com.musinsacoordi.backend.domain.product.error.ProductException;
import com.musinsacoordi.backend.domain.product.validation.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductValidator productValidator;

    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        // 비즈니스 규칙 검증
        productValidator.validateCreate(requestDto);

        // 연관 엔티티 조회
        Brand brand = brandRepository.findById(requestDto.getBrandId())
                .orElseThrow(() -> new ProductException(ProductErrorCode.INVALID_PRODUCT_INFO, "존재하지 않는 브랜드"));

        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new ProductException(ProductErrorCode.INVALID_PRODUCT_INFO, "존재하지 않는 카테고리"));

        // 엔티티 생성 및 저장
        Product product = Product.builder()
                .brand(brand)
                .category(category)
                .price(requestDto.getPrice())
                .build();

        Product savedProduct = productRepository.save(product);
        return ProductResponseDto.of(savedProduct);
    }

    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponseDto::of)
                .toList();
    }

    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND, id));
        return ProductResponseDto.of(product);
    }

    /**
     * 브랜드별 상품 조회
     */
    public List<ProductResponseDto> getProductsByBrandId(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new ProductException(ProductErrorCode.INVALID_PRODUCT_INFO, "존재하지 않는 브랜드"));

        return productRepository.findByBrand(brand).stream()
                .map(ProductResponseDto::of)
                .toList();
    }

    /**
     * 카테고리별 상품 조회
     */
    public List<ProductResponseDto> getProductsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ProductException(ProductErrorCode.INVALID_PRODUCT_INFO, "존재하지 않는 카테고리"));

        return productRepository.findByCategory(category).stream()
                .map(ProductResponseDto::of)
                .toList();
    }

    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto) {
        // 비즈니스 규칙 검증
        productValidator.validateUpdate(id, requestDto);

        // 엔티티 조회
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND, id));

        // 연관 엔티티 조회
        Brand brand = brandRepository.findById(requestDto.getBrandId())
                .orElseThrow(() -> new ProductException(ProductErrorCode.INVALID_PRODUCT_INFO, "존재하지 않는 브랜드"));

        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new ProductException(ProductErrorCode.INVALID_PRODUCT_INFO, "존재하지 않는 카테고리"));

        // 엔티티 수정
        product.update(brand, category, requestDto.getPrice());
        return ProductResponseDto.of(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND, id));
        productRepository.delete(product);
    }
}