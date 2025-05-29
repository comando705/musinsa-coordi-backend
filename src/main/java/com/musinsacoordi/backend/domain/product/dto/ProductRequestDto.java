package com.musinsacoordi.backend.domain.product.dto;

import com.musinsacoordi.backend.domain.product.validation.ValidPrice;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "상품 요청 DTO")
public class ProductRequestDto {
    @NotNull(message = "브랜드 ID는 필수입니다.")
    @Schema(description = "브랜드 ID", example = "1")
    private Long brandId;

    @NotNull(message = "카테고리 ID는 필수입니다.")
    @Schema(description = "카테고리 ID", example = "1")
    private Long categoryId;

    @NotNull(message = "가격은 필수입니다.")
    @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    @Schema(description = "상품 가격", example = "10000")
    private Integer price;
}