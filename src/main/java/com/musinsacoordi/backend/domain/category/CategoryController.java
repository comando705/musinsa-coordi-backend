package com.musinsacoordi.backend.domain.category;

import com.musinsacoordi.backend.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.musinsacoordi.backend.domain.category.dto.CategoryRequestDto;
import com.musinsacoordi.backend.domain.category.dto.CategoryResponseDto;

import java.net.URI;
import java.util.List;

@Tag(name = "카테고리 API", description = "카테고리 관련 API")
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 생성", description = "새로운 카테고리를 생성합니다.")
    @PostMapping
    public ResponseEntity<BaseResponse<CategoryResponseDto>> createCategory(
            @Parameter(description = "카테고리 생성 요청 정보", required = true)
            @Valid @RequestBody CategoryRequestDto requestDto
    ) {
        CategoryResponseDto responseDto = categoryService.createCategory(requestDto);
        return ResponseEntity
                .created(URI.create("/api/v1/categories/" + responseDto.getId()))
                .body(BaseResponse.success(responseDto));
    }

    @Operation(summary = "카테고리 목록 조회", description = "전체 카테고리 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<BaseResponse<List<CategoryResponseDto>>> getCategories() {
        List<CategoryResponseDto> responseDtoList = categoryService.getCategories();
        return ResponseEntity.ok(BaseResponse.success(responseDtoList));
    }

    @Operation(summary = "카테고리 단건 조회", description = "특정 카테고리를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<CategoryResponseDto>> getCategory(
            @Parameter(description = "카테고리 ID", required = true)
            @PathVariable Long id
    ) {
        CategoryResponseDto responseDto = categoryService.getCategory(id);
        return ResponseEntity.ok(BaseResponse.success(responseDto));
    }

    @Operation(summary = "카테고리 수정", description = "특정 카테고리의 정보를 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<CategoryResponseDto>> updateCategory(
            @Parameter(description = "카테고리 ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "카테고리 수정 요청 정보", required = true)
            @Valid @RequestBody CategoryRequestDto requestDto
    ) {
        CategoryResponseDto responseDto = categoryService.updateCategory(id, requestDto);
        return ResponseEntity.ok(BaseResponse.success(responseDto));
    }

    @Operation(summary = "카테고리 삭제", description = "특정 카테고리를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "카테고리 ID", required = true)
            @PathVariable Long id
    ) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
