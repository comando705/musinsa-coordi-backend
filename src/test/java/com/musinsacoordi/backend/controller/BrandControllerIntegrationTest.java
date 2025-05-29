package com.musinsacoordi.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsacoordi.backend.MusinsaCoordiBackendApplication;
import com.musinsacoordi.backend.domain.brand.BrandRepository;
import com.musinsacoordi.backend.domain.brand.dto.BrandRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = MusinsaCoordiBackendApplication.class)
@AutoConfigureMockMvc
@Transactional // 테스트 후 롤백하여 데이터 일관성 유지
public class BrandControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // JSON 직렬화를 위해 필요

    @Autowired
    private BrandRepository brandRepository;

    // 각 테스트 실행 전 데이터 초기화 또는 설정
    @BeforeEach
    void setUp() {
        // 데이터베이스 초기화 (data.sql에 의해 이미 초기 데이터가 삽입되지만, 테스트 간 독립성을 위해 추가 초기화 가능)
        // 여기서는 @Transactional 덕분에 롤백되므로 별도의 초기화는 필요 없음
    }

    // 각 테스트 실행 후 데이터 정리
    @AfterEach
    void tearDown() {
        brandRepository.deleteAll(); // 테스트마다 데이터 삭제하여 독립성 보장
    }

    @Test
    @DisplayName("새로운 브랜드를 성공적으로 생성한다")
    void createBrand_Success() throws Exception {
        BrandRequestDto requestDto = BrandRequestDto.builder()
                .name("TestBrand")
                .build();

        mockMvc.perform(post("/api/brands") //
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated()) // HTTP 201 Created 확인
                .andExpect(jsonPath("$.data.name").value("TestBrand")); //
    }

    @Test
    @DisplayName("모든 브랜드를 성공적으로 조회한다")
    void getAllBrands_Success() throws Exception {
        // data.sql에 의해 기본 브랜드가 존재한다고 가정
        mockMvc.perform(get("/api/brands") //
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // HTTP 200 OK 확인
                .andExpect(jsonPath("$.data").isArray()) //
                .andExpect(jsonPath("$.data.length()").isNotEmpty()); //
    }

    @Test
    @DisplayName("ID로 특정 브랜드를 성공적으로 조회한다")
    void getBrandById_Success() throws Exception {
        // 테스트용 브랜드 생성 (데이터베이스에 미리 저장)
        BrandRequestDto createDto = BrandRequestDto.builder().name("UniqueBrand").build();
        mockMvc.perform(post("/api/brands") //
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)));

        mockMvc.perform(get("/api/brands/1") // // data.sql에 의해 id 1은 이미 존재할 가능성 높음
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // HTTP 200 OK 확인
                .andExpect(jsonPath("$.data.id").isNumber()); //
    }

    @Test
    @DisplayName("ID로 브랜드를 성공적으로 수정한다")
    void updateBrand_Success() throws Exception {
        // 테스트용 브랜드 생성
        BrandRequestDto createDto = BrandRequestDto.builder().name("UpdateOriginalBrand").build();
        mockMvc.perform(post("/api/brands") //
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)));

        BrandRequestDto updateDto = BrandRequestDto.builder()
                .name("UpdatedBrandName")
                .build();

        mockMvc.perform(put("/api/brands/1") // // data.sql에 의해 id 1은 이미 존재할 가능성 높음
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk()) // HTTP 200 OK 확인
                .andExpect(jsonPath("$.data.name").value("UpdatedBrandName")); //
    }

    @Test
    @DisplayName("ID로 브랜드를 성공적으로 삭제한다")
    void deleteBrand_Success() throws Exception {
        // 테스트용 브랜드 생성
        BrandRequestDto createDto = BrandRequestDto.builder().name("DeleteBrand").build();
        mockMvc.perform(post("/api/brands") //
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)));

        mockMvc.perform(delete("/api/brands/1") // // data.sql에 의해 id 1은 이미 존재할 가능성 높음
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // HTTP 204 No Content 확인
    }
}