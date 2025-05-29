package com.musinsacoordi.backend.domain.product;

import com.musinsacoordi.backend.common.dto.BaseResponse;
import com.musinsacoordi.backend.common.dto.ErrorResponseWrappers;
import com.musinsacoordi.backend.common.dto.ResponseWrappers;
import com.musinsacoordi.backend.domain.product.dto.ProductRequestDto;
import com.musinsacoordi.backend.domain.product.dto.ProductResponseDto;
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

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "상품 API")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 생성", description = "새로운 상품을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "상품 생성 성공",
                    content = @Content(schema = @Schema(implementation = ResponseWrappers.ProductResponseWrapper.class))),
            @ApiResponse(responseCode = "404", description = "브랜드 또는 카테고리를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponseWrappers.ErrorResponseWrapper.class)))
    })
    @PostMapping
    public ResponseEntity<BaseResponse<ProductResponseDto>> createProduct(
            @Valid @RequestBody ProductRequestDto requestDto) {
        ProductResponseDto responseDto = productService.createProduct(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(responseDto));
    }

    @Operation(summary = "상품 전체 조회", description = "모든 상품을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 조회 성공",
                    content = @Content(schema = @Schema(implementation = ResponseWrappers.ProductListResponseWrapper.class)))
    })
    @GetMapping
    public ResponseEntity<BaseResponse<List<ProductResponseDto>>> getAllProducts() {
        List<ProductResponseDto> responseDtoList = productService.getAllProducts();
        return ResponseEntity.ok(BaseResponse.success(responseDtoList));
    }

    @Operation(summary = "상품 단일 조회", description = "ID로 상품을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 조회 성공",
                    content = @Content(schema = @Schema(implementation = ResponseWrappers.ProductResponseWrapper.class))),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponseWrappers.ErrorResponseWrapper.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ProductResponseDto>> getProductById(
            @Parameter(description = "상품 ID", required = true, example = "1")
            @PathVariable Long id) {
        ProductResponseDto responseDto = productService.getProductById(id);
        return ResponseEntity.ok(BaseResponse.success(responseDto));
    }

    @Operation(summary = "브랜드별 상품 조회", description = "브랜드 ID로 상품을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 조회 성공",
                    content = @Content(schema = @Schema(implementation = ResponseWrappers.ProductListResponseWrapper.class))),
            @ApiResponse(responseCode = "404", description = "브랜드를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponseWrappers.ErrorResponseWrapper.class)))
    })
    @GetMapping("/brand/{brandId}")
    public ResponseEntity<BaseResponse<List<ProductResponseDto>>> getProductsByBrandId(
            @Parameter(description = "브랜드 ID", required = true, example = "1")
            @PathVariable Long brandId) {
        List<ProductResponseDto> responseDtoList = productService.getProductsByBrandId(brandId);
        return ResponseEntity.ok(BaseResponse.success(responseDtoList));
    }

    @Operation(summary = "카테고리별 상품 조회", description = "카테고리 ID로 상품을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 조회 성공",
                    content = @Content(schema = @Schema(implementation = ResponseWrappers.ProductListResponseWrapper.class))),
            @ApiResponse(responseCode = "404", description = "카테고리를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponseWrappers.ErrorResponseWrapper.class)))
    })
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<BaseResponse<List<ProductResponseDto>>> getProductsByCategoryId(
            @Parameter(description = "카테고리 ID", required = true, example = "1")
            @PathVariable Long categoryId) {
        List<ProductResponseDto> responseDtoList = productService.getProductsByCategoryId(categoryId);
        return ResponseEntity.ok(BaseResponse.success(responseDtoList));
    }

    @Operation(summary = "상품 수정", description = "상품 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 수정 성공",
                    content = @Content(schema = @Schema(implementation = ResponseWrappers.ProductResponseWrapper.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ErrorResponseWrappers.ErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponseWrappers.ErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 상품",
                    content = @Content(schema = @Schema(implementation = ErrorResponseWrappers.ErrorResponseWrapper.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<ProductResponseDto>> updateProduct(
            @Parameter(description = "상품 ID", required = true, example = "1")
            @PathVariable Long id,
            @RequestBody @Valid ProductRequestDto requestDto) {
        ProductResponseDto responseDto = productService.updateProduct(id, requestDto);
        return ResponseEntity.ok(BaseResponse.success(responseDto));
    }

    @Operation(summary = "상품 삭제", description = "ID로 상품을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "상품 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponseWrappers.ErrorResponseWrapper.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "상품 ID", required = true, example = "1")
            @PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
