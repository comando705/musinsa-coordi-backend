package com.musinsacoordi.backend.domain.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.musinsacoordi.backend.common.error.BaseException;
import com.musinsacoordi.backend.common.error.ErrorCode;
import com.musinsacoordi.backend.domain.category.dto.CategoryRequestDto;
import com.musinsacoordi.backend.domain.category.dto.CategoryResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * 카테고리 생성
     */
    @Transactional
    public CategoryResponseDto createCategory(CategoryRequestDto requestDto) {
        // 이름 중복 검사
        if (categoryRepository.findByName(requestDto.getName()).isPresent()) {
            throw new BaseException(ErrorCode.ALREADY_EXIST_ENTITY, "카테고리", requestDto.getName());
        }
        
        Category category = requestDto.toEntity();
        Category savedCategory = categoryRepository.save(category);
        return CategoryResponseDto.of(savedCategory);
    }

    /**
     * 카테고리 전체 조회
     */
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryResponseDto::of)
                .collect(Collectors.toList());
    }

    /**
     * 카테고리 단일 조회
     */
    public CategoryResponseDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND, "카테고리", id));
        return CategoryResponseDto.of(category);
    }

    /**
     * 카테고리 수정
     */
    @Transactional
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto requestDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND, "카테고리", id));
        
        // 이름 중복 검사 (자기 자신 제외)
        categoryRepository.findByName(requestDto.getName())
                .ifPresent(existingCategory -> {
                    if (!existingCategory.getId().equals(id)) {
                        throw new BaseException(ErrorCode.ALREADY_EXIST_ENTITY, "카테고리", requestDto.getName());
                    }
                });
        
        category.updateName(requestDto.getName());
        return CategoryResponseDto.of(category);
    }

    /**
     * 카테고리 삭제
     */
    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND, "카테고리", id));
        categoryRepository.delete(category);
    }
}