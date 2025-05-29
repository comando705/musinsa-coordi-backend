package com.musinsacoordi.backend.domain.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandLowestTotalPriceDto {
    
    @Schema(description = "브랜드 ID", example = "1")
    private Long brandId;
    
    @Schema(description = "브랜드 이름", example = "A")
    private String brandName;
    
    @Schema(description = "브랜드 상품 목록")
    private List<ProductResponseDto> products;
    
    @Schema(description = "총액", example = "245000")
    private Integer totalPrice;
}