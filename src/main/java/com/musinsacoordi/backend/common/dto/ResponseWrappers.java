package com.musinsacoordi.backend.common.dto;

import com.musinsacoordi.backend.domain.brand.dto.BrandResponseDto;
import com.musinsacoordi.backend.domain.category.dto.CategoryResponseDto;
import com.musinsacoordi.backend.domain.product.dto.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Response wrapper classes for Swagger documentation.
 * These classes are used only for OpenAPI documentation purposes.
 */
public class ResponseWrappers {

    @Schema(description = "카테고리별 최저가 브랜드 조회 응답")
    public static class CategoryPriceRangeResponseWrapper extends BaseResponse<CategoryPriceRangeDto> {
        public CategoryPriceRangeResponseWrapper() {
            super(null);
        }
    }

    @Schema(description = "단일 브랜드 최저가 조회 응답")
    public static class BrandLowestTotalPriceWrapper extends BaseResponse<BrandLowestTotalPriceDto> {
        public BrandLowestTotalPriceWrapper() {
            super(null);
        }
    }

    @Schema(description = "카테고리별 최저가/최고가 브랜드 조회 응답 (한글)")
    public static class CategoryPriceMinMaxKoWrapper extends BaseResponse<CategoryPriceMinMaxKoDto> {
        public CategoryPriceMinMaxKoWrapper() {
            super(null);
        }
    }

    @Schema(description = "카테고리별 최저가/최고가 브랜드 조회 응답 (영문)")
    public static class CategoryPriceMinMaxWrapper extends BaseResponse<CategoryPriceMinMaxDto> {
        public CategoryPriceMinMaxWrapper() {
            super(null);
        }
    }

    @Schema(description = "브랜드 응답")
    public static class BrandResponseWrapper extends BaseResponse<BrandResponseDto> {
        public BrandResponseWrapper() {
            super(null);
        }
    }

    @Schema(description = "브랜드 목록 응답")
    public static class BrandListResponseWrapper extends BaseResponse<List<BrandResponseDto>> {
        public BrandListResponseWrapper() {
            super(null);
        }
    }

    @Schema(description = "카테고리 응답")
    public static class CategoryResponseWrapper extends BaseResponse<CategoryResponseDto> {
        public CategoryResponseWrapper() {
            super(null);
        }
    }

    @Schema(description = "카테고리 목록 응답")
    public static class CategoryListResponseWrapper extends BaseResponse<List<CategoryResponseDto>> {
        public CategoryListResponseWrapper() {
            super(null);
        }
    }

    @Schema(description = "상품 응답")
    public static class ProductResponseWrapper extends BaseResponse<ProductResponseDto> {
        public ProductResponseWrapper() {
            super(null);
        }
    }

    @Schema(description = "상품 목록 응답")
    public static class ProductListResponseWrapper extends BaseResponse<List<ProductResponseDto>> {
        public ProductListResponseWrapper() {
            super(null);
        }
    }
}
