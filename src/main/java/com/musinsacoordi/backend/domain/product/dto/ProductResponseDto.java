package com.musinsacoordi.backend.domain.product.dto;

import com.musinsacoordi.backend.domain.product.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {

    @Schema(description = "상품 ID", example = "1")
    private Long id;

    @Schema(description = "브랜드 ID", example = "1")
    private Long brandId;

    @Schema(description = "브랜드 이름", example = "나이키")
    private String brandName;

    @Schema(description = "카테고리 ID", example = "1")
    private Long categoryId;

    @Schema(description = "카테고리 이름", example = "상의")
    private String categoryName;

    @Schema(description = "가격", example = "10000")
    private Integer price;

    @Schema(description = "생성 일시", example = "2023-01-01T00:00:00")
    private LocalDateTime createDate;

    @Schema(description = "수정 일시", example = "2023-01-02T00:00:00")
    private LocalDateTime modifiedDate;

    public static ProductResponseDto of(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getBrand().getId(),
                product.getBrand().getName(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getPrice(),
                product.getCreateDate(),
                product.getModifiedDate()
        );
    }
}