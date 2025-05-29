package com.musinsacoordi.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsacoordi.backend.MusinsaCoordiBackendApplication;
import com.musinsacoordi.backend.domain.category.Category;
import com.musinsacoordi.backend.domain.category.CategoryRepository;
import com.musinsacoordi.backend.domain.category.dto.CategoryRequestDto;
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
@Transactional
class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    private Long testCategoryId;

    @BeforeEach
    void setUp() {
        // 기존 데이터 정리
        categoryRepository.deleteAll();

        // 테스트용 카테고리 생성
        Category testCategory = Category.builder()
                .name("TestCategory")
                .build();
        Category savedCategory = categoryRepository.save(testCategory);
        testCategoryId = savedCategory.getId();
    }

    @Test
    @DisplayName("새로운 카테고리를 성공적으로 생성한다")
    void createCategory_Success() throws Exception {
        CategoryRequestDto requestDto = CategoryRequestDto.builder()
                .name("NewTestCategory")
                .build();

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("NewTestCategory"));
    }

    @Test
    @DisplayName("ID로 카테고리를 성공적으로 수정한다")
    void updateCategory_Success() throws Exception {
        CategoryRequestDto updateDto = CategoryRequestDto.builder()
                .name("UpdatedCategoryName")
                .build();

        mockMvc.perform(put("/api/v1/categories/" + testCategoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("UpdatedCategoryName"));
    }

    @Test
    @DisplayName("모든 카테고리를 성공적으로 조회한다")
    void getCategories_Success() throws Exception {
        // data.sql에 의해 기본 카테고리가 존재한다고 가정
        mockMvc.perform(get("/api/v1/categories") //
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // HTTP 200 OK 확인
                .andExpect(jsonPath("$.data").isArray()) //
                .andExpect(jsonPath("$.data.length()").isNotEmpty()); //
    }

    @Test
    @DisplayName("ID로 특정 카테고리를 성공적으로 조회한다")
    void getCategory_Success() throws Exception {
        // 테스트용 카테고리 생성 (데이터베이스에 미리 저장)
        CategoryRequestDto createDto = CategoryRequestDto.builder().name("UniqueCategory").build();
        mockMvc.perform(post("/api/v1/categories") //
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)));

        mockMvc.perform(get("/api/v1/categories/1") // // data.sql에 의해 id 1은 이미 존재할 가능성 높음
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // HTTP 200 OK 확인
                .andExpect(jsonPath("$.data.id").isNumber()); //
    }

    @Test
    @DisplayName("ID로 카테고리를 성공적으로 삭제한다")
    void deleteCategory_Success() throws Exception {
        // 테스트용 카테고리 생성
        CategoryRequestDto createDto = CategoryRequestDto.builder().name("DeleteCategory").build();
        mockMvc.perform(post("/api/v1/categories") //
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)));

        mockMvc.perform(delete("/api/v1/categories/1") // // data.sql에 의해 id 1은 이미 존재할 가능성 높음
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // HTTP 204 No Content 확인
    }
}