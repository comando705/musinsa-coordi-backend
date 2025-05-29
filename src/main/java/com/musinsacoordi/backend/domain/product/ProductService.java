package com.musinsacoordi.backend.domain.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.musinsacoordi.backend.common.error.BaseException;
import com.musinsacoordi.backend.common.error.ErrorCode;
import com.musinsacoordi.backend.domain.brand.Brand;
import com.musinsacoordi.backend.domain.brand.BrandRepository;
import com.musinsacoordi.backend.domain.category.Category;
import com.musinsacoordi.backend.domain.category.CategoryRepository;
import com.musinsacoordi.backend.domain.product.dto.ProductRequestDto;
import com.musinsacoordi.backend.domain.product.dto.ProductResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    /**
     * 상품 생성
     */
    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        // 브랜드 조회
        Brand brand = brandRepository.findById(requestDto.getBrandId())
                .orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND, "브랜드", requestDto.getBrandId()));
        
        // 카테고리 조회
        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND, "카테고리", requestDto.getCategoryId()));
        
        // 브랜드와 카테고리 조합 중복 검사
        if (productRepository.findByBrandAndCategory(brand, category).isPresent()) {
            throw new BaseException(ErrorCode.ALREADY_EXIST_ENTITY, "상품", brand.getName() + "-" + category.getName());
        }
        
        Product product = Product.builder()
                .brand(brand)
                .category(category)
                .price(requestDto.getPrice())
                .build();
        
        Product savedProduct = productRepository.save(product);
        return ProductResponseDto.of(savedProduct);
    }

    /**
     * 상품 전체 조회
     */
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponseDto::of)
                .collect(Collectors.toList());
    }

    /**
     * 상품 단일 조회
     */
    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND, "상품", id));
        return ProductResponseDto.of(product);
    }

    /**
     * 브랜드별 상품 조회
     */
    public List<ProductResponseDto> getProductsByBrandId(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND, "브랜드", brandId));
        
        return productRepository.findByBrand(brand).stream()
                .map(ProductResponseDto::of)
                .collect(Collectors.toList());
    }

    /**
     * 카테고리별 상품 조회
     */
    public List<ProductResponseDto> getProductsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND, "카테고리", categoryId));
        
        return productRepository.findByCategory(category).stream()
                .map(ProductResponseDto::of)
                .collect(Collectors.toList());
    }

    /**
     * 상품 가격 수정
     */
    @Transactional
    public ProductResponseDto updateProductPrice(Long id, ProductRequestDto requestDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND, "상품", id));
        
        product.updatePrice(requestDto.getPrice());
        return ProductResponseDto.of(product);
    }

    /**
     * 상품 삭제
     */
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND, "상품", id));
        productRepository.delete(product);
    }
}