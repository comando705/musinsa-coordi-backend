package com.musinsacoordi.backend.domain.product;

import com.musinsacoordi.backend.common.dto.ErrorResponseWrappers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.musinsacoordi.backend.common.dto.BaseResponse;
import com.musinsacoordi.backend.common.dto.ResponseWrappers;
import com.musinsacoordi.backend.domain.product.dto.*;

@RestController
@RequestMapping("/api/products/query")
@RequiredArgsConstructor
@Tag(name = "Product Query", description = "상품 조회 API")
public class ProductQueryController {

    private final ProductQueryService productQueryService;

    @Operation(summary = "카테고리별 최저가 브랜드 조회 - 구현1", description = "전체 카테고리에서 각 카테고리별로 가장 낮은 가격의 상품을 가진 브랜드와 가격, 그리고 전체 카테고리 최저가의 총합을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = ResponseWrappers.CategoryPriceRangeResponseWrapper.class)))
    })
    @GetMapping("/lowest-price-by-categories")
    public ResponseEntity<BaseResponse<CategoryPriceRangeDto>> getPriceRangeByCategory() {
        CategoryPriceRangeDto responseDto = productQueryService.findPriceRangeByCategory();
        return ResponseEntity.ok(BaseResponse.success(responseDto));
    }

    @Operation(summary = "최저가격 브랜드 조회 - 구현2", description = "단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = ResponseWrappers.BrandLowestTotalPriceWrapper.class)))
    })
    @GetMapping("/lowest-price-brand")
    public ResponseEntity<BaseResponse<BrandLowestTotalPriceDto>> getBrandWithLowestTotalPrice() {
        BrandLowestTotalPriceDto responseDto = productQueryService.findBrandWithLowestTotalPrice();
        return ResponseEntity.ok(BaseResponse.success(responseDto));
    }

    @Operation(summary = "카테고리별 최저가/최고가 브랜드 조회 (한글) - 구현3", description = "지정된 카테고리에서 최저가격과 최고가격을 가진 브랜드와 가격을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = ResponseWrappers.CategoryPriceMinMaxKoWrapper.class))),
            @ApiResponse(responseCode = "404", description = "카테고리를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponseWrappers.ErrorResponseWrapper.class)))
    })
    @GetMapping("/category-price-range/ko/{categoryName}")
    public ResponseEntity<BaseResponse<CategoryPriceMinMaxKoDto>> getCategoryPriceRangeKo(
            @Parameter(description = "카테고리 이름", required = true, example = "상의")
            @PathVariable String categoryName) {
        CategoryPriceMinMaxDto responseDto = productQueryService.findMinMaxPriceByCategoryName(categoryName);
        CategoryPriceMinMaxKoDto koResponseDto = CategoryPriceMinMaxKoDto.from(responseDto);
        return ResponseEntity.ok(BaseResponse.success(koResponseDto));
    }

    @Operation(summary = "Category Min/Max Price Brand Query (English) - 구현3",
               description = "Find brands with the lowest and highest prices for a specified category.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Query successful",
                    content = @Content(schema = @Schema(implementation = ResponseWrappers.CategoryPriceMinMaxWrapper.class))),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseWrappers.ErrorResponseWrapper.class)))
    })
    @GetMapping("/category-price-range/en/{categoryName}")
    public ResponseEntity<BaseResponse<CategoryPriceMinMaxDto>> getCategoryPriceRangeEn(
            @Parameter(description = "Category name", required = true, example = "Top")
            @PathVariable String categoryName) {
        CategoryPriceMinMaxDto responseDto = productQueryService.findMinMaxPriceByCategoryName(categoryName);
        return ResponseEntity.ok(BaseResponse.success(responseDto));
    }
}
