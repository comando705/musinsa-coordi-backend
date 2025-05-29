package com.musinsacoordi.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsacoordi.backend.domain.product.ProductService;
import com.musinsacoordi.backend.domain.product.dto.ProductRequestDto;
import com.musinsacoordi.backend.domain.product.dto.ProductResponseDto;
import com.musinsacoordi.backend.domain.product.validation.ProductValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductValidator productValidator;

    private final LocalDateTime now = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        // 모든 validator 메서드에 대해 기본적으로 성공 처리
        doNothing().when(productValidator).validateCreate(any(ProductRequestDto.class));
        doNothing().when(productValidator).validateUpdate(any(Long.class), any(ProductRequestDto.class));
    }

    @Test
    @DisplayName("상품 생성 - 성공")
    void createProduct_Success() throws Exception {
        // given
        ProductRequestDto requestDto = new ProductRequestDto(1L, 1L, 10000);
        ProductResponseDto responseDto = new ProductResponseDto(1L, 1L, "브랜드명", 1L, "카테고리명", 10000, now, now);
        given(productService.createProduct(any(ProductRequestDto.class))).willReturn(responseDto);

        // when & then
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.price").value(10000));
    }

    @Test
    @DisplayName("전체 상품 조회 - 성공")
    void getAllProducts_Success() throws Exception {
        // given
        List<ProductResponseDto> responseDtoList = List.of(
                new ProductResponseDto(1L, 1L, "브랜드1", 1L, "카테고리1", 10000, now, now),
                new ProductResponseDto(2L, 2L, "브랜드2", 2L, "카테고리2", 20000, now, now)
        );
        given(productService.getAllProducts()).willReturn(responseDtoList);

        // when & then
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[1].id").value(2L));
    }

    @Test
    @DisplayName("상품 단일 조회 - 성공")
    void getProductById_Success() throws Exception {
        // given
        ProductResponseDto responseDto = new ProductResponseDto(1L, 1L, "브랜드명", 1L, "카테고리명", 10000, now, now);
        given(productService.getProductById(1L)).willReturn(responseDto);

        // when & then
        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L));
    }

    @Test
    @DisplayName("브랜드별 상품 조회 - 성공")
    void getProductsByBrandId_Success() throws Exception {
        // given
        List<ProductResponseDto> responseDtoList = List.of(
                new ProductResponseDto(1L, 1L, "브랜드1", 1L, "카테고리1", 10000, now, now),
                new ProductResponseDto(2L, 1L, "브랜드1", 2L, "카테고리2", 20000, now, now)
        );
        given(productService.getProductsByBrandId(1L)).willReturn(responseDtoList);

        // when & then
        mockMvc.perform(get("/api/products/brand/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[1].id").value(2L));
    }

    @Test
    @DisplayName("카테고리별 상품 조회 - 성공")
    void getProductsByCategoryId_Success() throws Exception {
        // given
        List<ProductResponseDto> responseDtoList = List.of(
                new ProductResponseDto(1L, 1L, "브랜드1", 1L, "카테고리1", 10000, now, now),
                new ProductResponseDto(2L, 2L, "브랜드2", 1L, "카테고리1", 20000, now, now)
        );
        given(productService.getProductsByCategoryId(1L)).willReturn(responseDtoList);

        // when & then
        mockMvc.perform(get("/api/products/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[1].id").value(2L));
    }

    @Test
    @DisplayName("상품 수정 - 성공")
    void updateProduct_Success() throws Exception {
        // given
        ProductRequestDto requestDto = new ProductRequestDto(1L, 1L, 20000);
        ProductResponseDto responseDto = new ProductResponseDto(1L, 1L, "브랜드명", 1L, "카테고리명", 20000, now, now);
        given(productService.updateProduct(eq(1L), any(ProductRequestDto.class))).willReturn(responseDto);

        // when & then
        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.price").value(20000));
    }

    @Test
    @DisplayName("상품 삭제 - 성공")
    void deleteProduct_Success() throws Exception {
        // given
        doNothing().when(productService).deleteProduct(1L);

        // when & then
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }
} 