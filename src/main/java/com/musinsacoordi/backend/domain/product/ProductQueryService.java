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
import com.musinsacoordi.backend.domain.product.dto.*;

import java.util.*;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    /**
     * 단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가격 브랜드와 총액 조회
     */
    public BrandLowestTotalPriceDto findBrandWithLowestTotalPrice() {
        List<Brand> brands = brandRepository.findAll();
        List<Category> categories = categoryRepository.findAll();

        Brand lowestTotalPriceBrand = null;
        int lowestTotalPrice = Integer.MAX_VALUE;
        List<Product> lowestTotalPriceProducts = null;

        for (Brand brand : brands) {
            List<Product> brandProducts = productRepository.findByBrand(brand);

            int totalPrice = brandProducts.stream()
                    .mapToInt(Product::getPrice)
                    .sum();

            if (totalPrice < lowestTotalPrice) {
                lowestTotalPrice = totalPrice;
                lowestTotalPriceBrand = brand;
                lowestTotalPriceProducts = brandProducts;
            }
        }

        List<ProductResponseDto> productDtos = lowestTotalPriceProducts.stream()
                .map(ProductResponseDto::of)
                .collect(Collectors.toList());

        return BrandLowestTotalPriceDto.builder()
                .brandId(lowestTotalPriceBrand.getId())
                .brandName(lowestTotalPriceBrand.getName())
                .products(productDtos)
                .totalPrice(lowestTotalPrice)
                .build();
    }

    /**
     * 전체 카테고리 상품 중 카테고리별로 가장 낮은 가격의 상품을 가진 브랜드와 가격 조회
     */
    public CategoryPriceRangeDto findPriceRangeByCategory() {
        List<Category> categories = categoryRepository.findAll();
        List<Product> allProducts = productRepository.findAll();

        Map<Long, Product> lowestPriceProductByCategory = categories.stream()
                .collect(Collectors.toMap(
                        Category::getId,
                        category -> allProducts.stream()
                                .filter(p -> p.getCategory().getId().equals(category.getId()))
                                .min(Comparator.comparing(Product::getPrice)
                                .thenComparing((Product p) -> p.getBrand().getId(), Comparator.reverseOrder()))
                        .orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND,
                                "카테고리 " + category.getName() + "의 상품", ""))
        ));

        // DTO 생성을 위한 리스트 준비
        List<CategoryLowestPriceDto> categoryLowestPrices = categories.stream()
            .map(category -> {
                Product lowestProduct = lowestPriceProductByCategory.get(category.getId());
                return CategoryLowestPriceDto.builder()
                        .categoryId(category.getId())
                        .categoryName(category.getName())
                        .brandId(lowestProduct.getBrand().getId())
                        .brandName(lowestProduct.getBrand().getName())
                        .price(lowestProduct.getPrice())
                        .build();
            })
            .collect(Collectors.toList());

        // 전체 카테고리 최저가 합계 계산
        int totalPrice = categoryLowestPrices.stream()
                .mapToInt(CategoryLowestPriceDto::getPrice)
                .sum();

        return CategoryPriceRangeDto.builder()
            .categories(categoryLowestPrices)
            .totalPrice(totalPrice)
            .build();
    }

    /**
     * 카테고리 이름으로 최저가격 및 최고가격 브랜드와 가격 조회
     * @param categoryName 카테고리 이름
     * @return 카테고리의 최저가격 및 최고가격 브랜드 정보
     */
    public CategoryPriceMinMaxDto findMinMaxPriceByCategoryName(String categoryName) {
        // 카테고리 이름으로 카테고리 조회
        Category category = findCategoryByNameOrThrow(categoryName);
        
        // 해당 카테고리의 모든 상품 조회
        List<Product> categoryProducts = findProductsByCategoryOrThrow(category, categoryName);
        
        // 최저가/최고가 상품 찾기
        Product lowestPriceProduct = findProductWithExtremePrice(categoryProducts, categoryName, true);
        Product highestPriceProduct = findProductWithExtremePrice(categoryProducts, categoryName, false);
        
        // DTO 생성 및 반환
        return buildCategoryPriceMinMaxDto(
                category.getName(),
                createBrandPriceDto(lowestPriceProduct),
                createBrandPriceDto(highestPriceProduct)
        );
    }

    private Category findCategoryByNameOrThrow(String categoryName) {
        return categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND, 
                        "카테고리 " + categoryName, ""));
    }

    private List<Product> findProductsByCategoryOrThrow(Category category, String categoryName) {
        List<Product> products = productRepository.findByCategory(category);
        if (products.isEmpty()) {
            throw new BaseException(ErrorCode.ENTITY_NOT_FOUND, 
                "카테고리 " + categoryName + "의 상품", "");
        }
        return products;
    }

    private Product findProductWithExtremePrice(List<Product> products, String categoryName, boolean findLowest) {
        Comparator<Product> priceComparator = Comparator.comparing(Product::getPrice);
        
        if (!findLowest) {
            priceComparator = priceComparator.reversed();
        }
        
        String extremeType = findLowest ? "최저가" : "최고가";
        
        return products.stream()
                .min(priceComparator)
                .orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND, 
                    "카테고리 " + categoryName + "의 " + extremeType + " 상품", ""));
    }

    private CategoryPriceMinMaxDto.BrandPriceDto createBrandPriceDto(Product product) {
        return CategoryPriceMinMaxDto.BrandPriceDto.builder()
                .brand(product.getBrand().getName())
                .price(String.format("%,d", product.getPrice()))
                .build();
    }

    private CategoryPriceMinMaxDto buildCategoryPriceMinMaxDto(
        String categoryName, 
        CategoryPriceMinMaxDto.BrandPriceDto lowestPriceDto,
        CategoryPriceMinMaxDto.BrandPriceDto highestPriceDto) {
        
        return CategoryPriceMinMaxDto.builder()
                .category(categoryName)
                .lowestPrice(Collections.singletonList(lowestPriceDto))
                .highestPrice(Collections.singletonList(highestPriceDto))
                .build();
    }
}