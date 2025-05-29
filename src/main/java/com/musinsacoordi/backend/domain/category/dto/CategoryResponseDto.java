package com.musinsacoordi.backend.domain.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.musinsacoordi.backend.domain.category.Category;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {
    
    @Schema(description = "카테고리 ID", example = "1")
    private Long id;
    
    @Schema(description = "카테고리 이름", example = "상의")
    private String name;
    
    @Schema(description = "생성 일시", example = "2023-01-01T00:00:00")
    private LocalDateTime createDate;
    
    @Schema(description = "수정 일시", example = "2023-01-02T00:00:00")
    private LocalDateTime modifiedDate;
    
    public static CategoryResponseDto of(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .createDate(category.getCreateDate())
                .modifiedDate(category.getModifiedDate())
                .build();
    }
}