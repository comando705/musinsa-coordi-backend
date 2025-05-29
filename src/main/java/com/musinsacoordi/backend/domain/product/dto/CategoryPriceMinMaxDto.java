package com.musinsacoordi.backend.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CategoryPriceMinMaxDto {

    @Schema(description = "카테고리 이름", example = "상의")
    @JsonProperty("category")
    private String category;

    @Schema(description = "최저가 브랜드 정보")
    @JsonProperty("lowestPrice")
    private List<BrandPriceDto> lowestPrice;

    @Schema(description = "최고가 브랜드 정보")
    @JsonProperty("highestPrice")
    private List<BrandPriceDto> highestPrice;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BrandPriceDto {
        @Schema(description = "브랜드 이름", example = "C")
        @JsonProperty("brand")
        private String brand;

        @Schema(description = "가격", example = "10,000")
        @JsonProperty("price")
        private String price;
    }
}
