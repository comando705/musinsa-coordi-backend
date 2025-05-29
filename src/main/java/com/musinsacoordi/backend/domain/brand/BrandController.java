package com.musinsacoordi.backend.domain.brand;

import com.musinsacoordi.backend.common.dto.BaseResponse;
import com.musinsacoordi.backend.common.dto.ErrorResponseWrappers;
import com.musinsacoordi.backend.common.dto.ResponseWrappers;
import com.musinsacoordi.backend.domain.brand.dto.BrandRequestDto;
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

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
@Tag(name = "Brand", description = "브랜드 API")
public class BrandController {

    private final BrandService brandService;

    @Operation(summary = "브랜드 생성", description = "새로운 브랜드를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "브랜드 생성 성공",
                    content = @Content(schema = @Schema(implementation = ResponseWrappers.BrandResponseWrapper.class))),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 브랜드",
                    content = @Content(schema = @Schema(implementation = ErrorResponseWrappers.ErrorResponseWrapper.class)))
    })
    @PostMapping
    public ResponseEntity<BaseResponse<BrandResponseDto>> createBrand(
            @Valid @RequestBody BrandRequestDto requestDto) {
        BrandResponseDto responseDto = brandService.createBrand(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(responseDto));
    }

    @Operation(summary = "브랜드 전체 조회", description = "모든 브랜드를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "브랜드 조회 성공",
                    content = @Content(schema = @Schema(implementation = ResponseWrappers.BrandListResponseWrapper.class)))
    })
    @GetMapping
    public ResponseEntity<BaseResponse<List<BrandResponseDto>>> getAllBrands() {
        List<BrandResponseDto> responseDtoList = brandService.getAllBrands();
        return ResponseEntity.ok(BaseResponse.success(responseDtoList));
    }

    @Operation(summary = "브랜드 단일 조회", description = "ID로 브랜드를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "브랜드 조회 성공",
                    content = @Content(schema = @Schema(implementation = ResponseWrappers.BrandResponseWrapper.class))),
            @ApiResponse(responseCode = "404", description = "브랜드를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponseWrappers.ErrorResponseWrapper.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<BrandResponseDto>> getBrandById(
            @Parameter(description = "브랜드 ID", required = true, example = "1")
            @PathVariable Long id) {
        BrandResponseDto responseDto = brandService.getBrandById(id);
        return ResponseEntity.ok(BaseResponse.success(responseDto));
    }

    @Operation(summary = "브랜드 수정", description = "ID로 브랜드를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "브랜드 수정 성공",
                    content = @Content(schema = @Schema(implementation = ResponseWrappers.BrandResponseWrapper.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ErrorResponseWrappers.ErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "404", description = "브랜드를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponseWrappers.ErrorResponseWrapper.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<BrandResponseDto>> updateBrand(
            @Parameter(description = "브랜드 ID", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody BrandRequestDto requestDto) {
        BrandResponseDto responseDto = brandService.updateBrand(id, requestDto);
        return ResponseEntity.ok(BaseResponse.success(responseDto));
    }

    @Operation(summary = "브랜드 삭제", description = "ID로 브랜드를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "브랜드 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "브랜드를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponseWrappers.ErrorResponseWrapper.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(
            @Parameter(description = "브랜드 ID", required = true, example = "1")
            @PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }
}