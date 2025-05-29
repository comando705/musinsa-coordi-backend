package com.musinsacoordi.backend.domain.brand.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.musinsacoordi.backend.domain.brand.Brand;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandResponseDto {
    
    @Schema(description = "브랜드 ID", example = "1")
    private Long id;
    
    @Schema(description = "브랜드 이름", example = "나이키")
    private String name;
    
    @Schema(description = "생성 일시", example = "2023-01-01T00:00:00")
    private LocalDateTime createDate;
    
    @Schema(description = "수정 일시", example = "2023-01-02T00:00:00")
    private LocalDateTime modifiedDate;
    
    public static BrandResponseDto of(Brand brand) {
        return BrandResponseDto.builder()
                .id(brand.getId())
                .name(brand.getName())
                .createDate(brand.getCreateDate())
                .modifiedDate(brand.getModifiedDate())
                .build();
    }
}