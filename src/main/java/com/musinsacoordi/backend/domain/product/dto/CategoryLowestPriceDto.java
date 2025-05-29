package com.musinsacoordi.backend.domain.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryLowestPriceDto {

    @Schema(description = "카테고리 ID", example = "1")
    private Long categoryId;

    @Schema(description = "카테고리 이름", example = "상의")
    private String categoryName;

    @Schema(description = "최저가 브랜드 ID", example = "1")
    private Long brandId;

    @Schema(description = "최저가 브랜드 이름", example = "D")
    private String brandName;

    @Schema(description = "최저가", example = "29900")
    private Integer price;
}
