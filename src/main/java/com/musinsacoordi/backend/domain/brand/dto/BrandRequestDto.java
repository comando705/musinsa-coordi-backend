package com.musinsacoordi.backend.domain.brand.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.musinsacoordi.backend.domain.brand.Brand;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandRequestDto {
    
    @NotBlank(message = "브랜드 이름은 필수입니다.")
    @Size(min = 1, max = 50, message = "브랜드 이름은 1자 이상 50자 이하여야 합니다.")
    @Schema(description = "브랜드 이름", example = "나이키")
    private String name;
    
    public Brand toEntity() {
        return Brand.builder()
                .name(name)
                .build();
    }
}