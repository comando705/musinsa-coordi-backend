package com.musinsacoordi.backend.common.service;

import com.musinsacoordi.backend.domain.brand.Brand;
import com.musinsacoordi.backend.domain.brand.BrandRepository;
import com.musinsacoordi.backend.domain.category.Category;
import com.musinsacoordi.backend.domain.category.CategoryRepository;
import com.musinsacoordi.backend.domain.product.Product;
import com.musinsacoordi.backend.domain.product.ProductRepository;
import com.musinsacoordi.backend.common.error.BaseException;
import com.musinsacoordi.backend.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 공통 데이터 서비스
 * 브랜드, 카테고리, 상품 관련 초기 데이터 삽입 및 조회 기능 제공
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommonDataService {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    /**
     * 참고: 데이터 초기화는 src/main/resources/data.sql 파일을 통해서만 이루어집니다.
     * application.properties에서 spring.sql.init.mode=always로 설정되어 있어
     * 애플리케이션 시작 시 자동으로 SQL 스크립트가 실행됩니다.
     */

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Brand findBrand(Long brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND, "Brand", brandId));
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category findCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND, "Category", categoryId));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product findProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND, "Product", productId));
    }
}
