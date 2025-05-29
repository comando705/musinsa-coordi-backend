package com.musinsacoordi.backend.domain.category;

import com.musinsacoordi.backend.common.dto.ErrorResponseWrappers;
import com.musinsacoordi.backend.domain.brand.dto.BrandResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.musinsacoordi.backend.common.dto.BaseResponse;
import com.musinsacoordi.backend.common.dto.ResponseWrappers;
import com.musinsacoordi.backend.domain.category.dto.CategoryRequestDto;
import com.musinsacoordi.backend.domain.category.dto.CategoryResponseDto;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category", description = "카테고리 API")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 생성", description = "새로운 카테고리를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "카테고리 생성 성공",
                    content = @Content(schema = @Schema(implementation = ResponseWrappers.CategoryResponseWrapper.class))),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 카테고리",
                    content = @Content(schema = @Schema(implementation = ErrorResponseWrappers.ErrorResponseWrapper.class)))
    })
    @PostMapping
    public ResponseEntity<BaseResponse<CategoryResponseDto>> createCategory(
            @Valid @RequestBody CategoryRequestDto requestDto) {
        CategoryResponseDto responseDto = categoryService.createCategory(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(responseDto));
    }

    @Operation(summary = "카테고리 전체 조회", description = "모든 카테고리를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리 조회 성공",
                    content = @Content(schema = @Schema(implementation = ResponseWrappers.CategoryListResponseWrapper.class)))
    })
    @GetMapping
    public ResponseEntity<BaseResponse<List<CategoryResponseDto>>> getAllCategories() {
        List<CategoryResponseDto> responseDtoList = categoryService.getAllCategories();
        return ResponseEntity.ok(BaseResponse.success(responseDtoList));
    }

    @Operation(summary = "카테고리 단일 조회", description = "ID로 카테고리를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리 조회 성공",
                    content = @Content(schema = @Schema(implementation = ResponseWrappers.CategoryResponseWrapper.class))),
            @ApiResponse(responseCode = "404", description = "카테고리를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponseWrappers.ErrorResponseWrapper.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<CategoryResponseDto>> getCategoryById(
            @Parameter(description = "카테고리 ID", required = true, example = "1")
            @PathVariable Long id) {
        CategoryResponseDto responseDto = categoryService.getCategoryById(id);
        return ResponseEntity.ok(BaseResponse.success(responseDto));
    }

    @Operation(summary = "카테고리 수정", description = "ID로 카테고리를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리 수정 성공",
                    content = @Content(schema = @Schema(implementation = ResponseWrappers.CategoryResponseWrapper.class))),
            @ApiResponse(responseCode = "404", description = "카테고리를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponseWrappers.ErrorResponseWrapper.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<CategoryResponseDto>> updateCategory(
            @Parameter(description = "카테고리 ID", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequestDto requestDto) {
        CategoryResponseDto responseDto = categoryService.updateCategory(id, requestDto);
        return ResponseEntity.ok(BaseResponse.success(responseDto));
    }

    @Operation(summary = "카테고리 삭제", description = "ID로 카테고리를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "카테고리 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "카테고리를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponseWrappers.ErrorResponseWrapper.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "카테고리 ID", required = true, example = "1")
            @PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
