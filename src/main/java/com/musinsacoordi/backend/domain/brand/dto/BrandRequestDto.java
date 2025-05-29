package com.musinsacoordi.backend.domain.brand.dto;

import com.musinsacoordi.backend.domain.brand.validation.ValidBrandName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.musinsacoordi.backend.domain.brand.Brand;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "브랜드 요청 DTO")
public class BrandRequestDto {
    @NotBlank(message = "브랜드 이름은 필수입니다.")
    @ValidBrandName(message = "브랜드 이름은 1자 이상 50자 이하여야 합니다.")
    @Schema(description = "브랜드 이름", example = "나이키")
    private String name;

    @Builder
    public BrandRequestDto(String name) {
        this.name = name;
    }

    public Brand toEntity() {
        return Brand.builder()
                .name(name)
                .build();
    }
}