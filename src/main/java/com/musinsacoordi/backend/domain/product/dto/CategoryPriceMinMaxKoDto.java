package com.musinsacoordi.backend.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Collections;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPriceMinMaxKoDto {

    @Schema(description = "카테고리 이름", example = "상의")
    @JsonProperty("카테고리")
    private String category;

    @Schema(description = "최저가 브랜드 정보")
    @JsonProperty("최저가")
    private List<BrandPriceKoDto> lowestPrice;

    @Schema(description = "최고가 브랜드 정보")
    @JsonProperty("최고가")
    private List<BrandPriceKoDto> highestPrice;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BrandPriceKoDto {
        @Schema(description = "브랜드 이름", example = "C")
        @JsonProperty("브랜드")
        private String brand;

        @Schema(description = "가격", example = "10,000")
        @JsonProperty("가격")
        private String price;
    }
    
    // Convert from CategoryPriceMinMaxDto to CategoryPriceMinMaxKoDto
    public static CategoryPriceMinMaxKoDto from(CategoryPriceMinMaxDto dto) {
        List<BrandPriceKoDto> lowestPriceDtos = dto.getLowestPrice().stream()
                .map(bp -> BrandPriceKoDto.builder()
                        .brand(bp.getBrand())
                        .price(bp.getPrice())
                        .build())
                .toList();
                
        List<BrandPriceKoDto> highestPriceDtos = dto.getHighestPrice().stream()
                .map(bp -> BrandPriceKoDto.builder()
                        .brand(bp.getBrand())
                        .price(bp.getPrice())
                        .build())
                .toList();
                
        return CategoryPriceMinMaxKoDto.builder()
                .category(dto.getCategory())
                .lowestPrice(lowestPriceDtos)
                .highestPrice(highestPriceDtos)
                .build();
    }
}