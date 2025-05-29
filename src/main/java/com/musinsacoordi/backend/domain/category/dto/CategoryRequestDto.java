package com.musinsacoordi.backend.domain.category.dto;

import com.musinsacoordi.backend.domain.category.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto {

    @NotBlank(message = "카테고리 이름은 필수입니다.")
    @Size(min = 1, max = 50, message = "카테고리 이름은 1자 이상 50자 이하여야 합니다.")
    @Schema(description = "카테고리 이름", example = "상의")
    private String name;

    public Category toEntity() {
        return Category.builder()
                .name(name)
                .build();
    }
}