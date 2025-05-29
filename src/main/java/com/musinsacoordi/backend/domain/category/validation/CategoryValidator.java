package com.musinsacoordi.backend.domain.category.validation;

import com.musinsacoordi.backend.domain.category.Category;
import com.musinsacoordi.backend.domain.category.CategoryRepository;
import com.musinsacoordi.backend.domain.category.dto.CategoryRequestDto;
import com.musinsacoordi.backend.domain.category.error.CategoryErrorCode;
import com.musinsacoordi.backend.domain.category.error.CategoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryValidator {
    private final CategoryRepository categoryRepository;

    public void validateCreate(CategoryRequestDto dto) {
        validateDuplicateName(dto.getName());
    }

    public void validateUpdate(Long categoryId, CategoryRequestDto dto) {
        validateCategoryExists(categoryId);
        validateDuplicateNameExceptSelf(categoryId, dto.getName());
    }

    private void validateCategoryExists(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryException(CategoryErrorCode.CATEGORY_NOT_FOUND, categoryId);
        }
    }

    private void validateDuplicateName(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new CategoryException(CategoryErrorCode.DUPLICATE_CATEGORY_NAME, name);
        }
    }

    private void validateDuplicateNameExceptSelf(Long categoryId, String name) {
        Category existingCategory = categoryRepository.findByName(name).orElse(null);
        if (existingCategory != null && !existingCategory.getId().equals(categoryId)) {
            throw new CategoryException(CategoryErrorCode.DUPLICATE_CATEGORY_NAME, name);
        }
    }
} 