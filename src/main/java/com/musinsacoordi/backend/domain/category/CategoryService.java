package com.musinsacoordi.backend.domain.category;

import com.musinsacoordi.backend.common.error.BaseException;
import com.musinsacoordi.backend.common.error.CommonErrorCode;
import com.musinsacoordi.backend.domain.category.dto.CategoryRequestDto;
import com.musinsacoordi.backend.domain.category.dto.CategoryResponseDto;
import com.musinsacoordi.backend.domain.product.Product;
import com.musinsacoordi.backend.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    /**
     * 카테고리 생성
     */
    @Transactional
    public CategoryResponseDto createCategory(CategoryRequestDto requestDto) {
        // 이름 중복 체크
        if (categoryRepository.existsByName(requestDto.getName())) {
            throw new BaseException(CommonErrorCode.ALREADY_EXIST_ENTITY, "카테고리", requestDto.getName());
        }

        Category category = Category.builder()
                .name(requestDto.getName())
                .build();

        return CategoryResponseDto.of(categoryRepository.save(category));
    }

    /**
     * 카테고리 목록 조회
     */
    public List<CategoryResponseDto> getCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryResponseDto::of)
                .toList();
    }

    /**
     * 카테고리 단건 조회
     */
    public CategoryResponseDto getCategory(Long id) {
        return categoryRepository.findById(id)
                .map(CategoryResponseDto::of)
                .orElseThrow(() -> new BaseException(CommonErrorCode.ENTITY_NOT_FOUND, "카테고리", id));
    }

    /**
     * 카테고리 수정
     */
    @Transactional
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto requestDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BaseException(CommonErrorCode.ENTITY_NOT_FOUND, "카테고리", id));

        // 이름이 변경되었고, 변경하려는 이름이 이미 존재하는 경우
        if (!category.getName().equals(requestDto.getName())
                && categoryRepository.existsByName(requestDto.getName())) {
            throw new BaseException(CommonErrorCode.ALREADY_EXIST_ENTITY, "카테고리", requestDto.getName());
        }

        category.updateName(requestDto.getName());
        return CategoryResponseDto.of(category);
    }

    /**
     * 카테고리 삭제
     */
    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .map(this::lockCategory)
                .orElseThrow(() -> new BaseException(CommonErrorCode.ENTITY_NOT_FOUND, "카테고리", id));

        categoryRepository.delete(category);
    }

    private Category lockCategory(Category category) {
        // 연관된 상품들에 대한 비관적 락 획득
        List<Product> products = productRepository.findByCategoryWithPessimisticLock(category);
        category.getProducts().clear();
        category.getProducts().addAll(products);
        return category;
    }
}