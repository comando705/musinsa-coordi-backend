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
public class CategoryPriceRangeDto {

    @Schema(description = "카테고리별 최저가 상품 정보 목록")
    private List<CategoryLowestPriceDto> categories;

    @Schema(description = "전체 카테고리 최저가 합계", example = "150000")
    private Integer totalPrice;
}
