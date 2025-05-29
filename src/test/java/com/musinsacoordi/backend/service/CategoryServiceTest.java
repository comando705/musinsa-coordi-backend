package com.musinsacoordi.backend.service;

import com.musinsacoordi.backend.common.error.BaseException; //
import com.musinsacoordi.backend.domain.category.Category; //
import com.musinsacoordi.backend.domain.category.CategoryRepository; //
import com.musinsacoordi.backend.domain.category.CategoryService; //
import com.musinsacoordi.backend.domain.category.dto.CategoryRequestDto; //
import com.musinsacoordi.backend.domain.category.dto.CategoryResponseDto; //
import com.musinsacoordi.backend.domain.product.Product; //
import com.musinsacoordi.backend.domain.product.ProductRepository; //
import com.musinsacoordi.backend.domain.brand.Brand; // // Brand 엔티티 임포트
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository; //

    @Mock
    private ProductRepository productRepository; //

    @InjectMocks
    private CategoryService categoryService; //

    private Category categoryA;

    @BeforeEach
    void setUp() {
        categoryA = Category.builder()
                .name("CategoryA")
                .build();
        
        // ID 설정
        try {
            java.lang.reflect.Field idField = Category.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(categoryA, 1L);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("카테고리 생성 성공")
    void createCategory_ValidInput_Success() {
        // Given
        CategoryRequestDto requestDto = CategoryRequestDto.builder()
                .name("NewCategory")
                .build();
        Category newCategory = Category.builder()
                .name(requestDto.getName())
                .build();
        try {
            java.lang.reflect.Field idField = Category.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(newCategory, 1L);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        when(categoryRepository.existsByName(requestDto.getName())).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(newCategory);

        // When
        CategoryResponseDto responseDto = categoryService.createCategory(requestDto);

        // Then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getName()).isEqualTo("NewCategory");
        assertThat(responseDto.getId()).isPositive();
        verify(categoryRepository).existsByName(requestDto.getName());
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    @DisplayName("카테고리 생성 실패: 이름 중복")
    void createCategory_DuplicateName_ThrowsException() {
        // Given
        CategoryRequestDto requestDto = CategoryRequestDto.builder()
                .name("ExistingCategory")
                .build();
        when(categoryRepository.existsByName(requestDto.getName())).thenReturn(true); //

        // When & Then
        assertThatThrownBy(() -> categoryService.createCategory(requestDto)) //
                .isInstanceOf(BaseException.class) //
                .hasMessageContaining("이미 존재하는 카테고리입니다. [ExistingCategory]"); //

        verify(categoryRepository, times(1)).existsByName(requestDto.getName()); //
        verify(categoryRepository, never()).save(any(Category.class)); //
    }

    @Test
    @DisplayName("모든 카테고리 조회 성공")
    void getCategories_Success() {
        // Given
        Category categoryB = Category.builder().name("CategoryB").build(); //
        List<Category> categories = Arrays.asList(categoryA, categoryB);
        when(categoryRepository.findAll()).thenReturn(categories); //

        // When
        List<CategoryResponseDto> responseDtos = categoryService.getCategories(); //

        // Then
        assertThat(responseDtos).hasSize(2);
        assertThat(responseDtos.get(0).getName()).isEqualTo("CategoryA");
        assertThat(responseDtos.get(1).getName()).isEqualTo("CategoryB");
        verify(categoryRepository, times(1)).findAll(); //
    }

    @Test
    @DisplayName("ID로 카테고리 조회 성공")
    void getCategory_Success() {
        // Given
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryA)); //

        // When
        CategoryResponseDto responseDto = categoryService.getCategory(categoryId); //

        // Then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getName()).isEqualTo("CategoryA");
        verify(categoryRepository, times(1)).findById(categoryId); //
    }

    @Test
    @DisplayName("ID로 카테고리 조회 실패: 카테고리를 찾을 수 없음")
    void getCategory_NotFound_ThrowsException() {
        // Given
        Long categoryId = 99L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty()); //

        // When & Then
        assertThatThrownBy(() -> categoryService.getCategory(categoryId)) //
                .isInstanceOf(BaseException.class) //
                .hasMessageContaining("카테고리를 찾을 수 없습니다. ID: " + categoryId); //

        verify(categoryRepository, times(1)).findById(categoryId); //
    }

    @Test
    @DisplayName("카테고리 수정 성공")
    void updateCategory_ValidInput_Success() {
        // Given
        Long categoryId = 1L;
        CategoryRequestDto requestDto = CategoryRequestDto.builder()
                .name("UpdatedCategoryName")
                .build();

        // 기존 카테고리 객체 Mocking
        Category existingCategory = Category.builder().name("OriginalCategory").build(); //
        try {
            java.lang.reflect.Field idField = Category.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(existingCategory, categoryId);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory)); //
        when(categoryRepository.existsByName(requestDto.getName())).thenReturn(false); //

        // When
        CategoryResponseDto responseDto = categoryService.updateCategory(categoryId, requestDto); //

        // Then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getName()).isEqualTo("UpdatedCategoryName");
        verify(categoryRepository, times(1)).findById(categoryId); //
        verify(categoryRepository, times(1)).existsByName(requestDto.getName()); //
        assertThat(existingCategory.getName()).isEqualTo("UpdatedCategoryName"); // 엔티티의 이름이 실제로 업데이트되었는지 확인
    }

    @Test
    @DisplayName("카테고리 수정 실패: 카테고리를 찾을 수 없음")
    void updateCategory_NotFound_ThrowsException() {
        // Given
        Long categoryId = 99L;
        CategoryRequestDto requestDto = CategoryRequestDto.builder().name("AnyName").build(); //
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty()); //

        // When & Then
        assertThatThrownBy(() -> categoryService.updateCategory(categoryId, requestDto)) //
                .isInstanceOf(BaseException.class) //
                .hasMessageContaining("카테고리를 찾을 수 없습니다. ID: " + categoryId); //

        verify(categoryRepository, times(1)).findById(categoryId); //
        verify(categoryRepository, never()).existsByName(anyString()); //
    }

    @Test
    @DisplayName("카테고리 수정 실패: 이름 중복")
    void updateCategory_DuplicateName_ThrowsException() {
        // Given
        Long categoryId = 1L;
        CategoryRequestDto requestDto = CategoryRequestDto.builder()
                .name("DuplicateCategoryName")
                .build();

        Category existingCategory = Category.builder().name("OriginalCategoryName").build(); //
        try {
            java.lang.reflect.Field idField = Category.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(existingCategory, categoryId);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory)); //
        when(categoryRepository.existsByName(requestDto.getName())).thenReturn(true); //

        // When & Then
        assertThatThrownBy(() -> categoryService.updateCategory(categoryId, requestDto)) //
                .isInstanceOf(BaseException.class) //
                .hasMessageContaining("이미 존재하는 카테고리입니다. [DuplicateCategoryName]"); //

        verify(categoryRepository, times(1)).findById(categoryId); //
        verify(categoryRepository, times(1)).existsByName(requestDto.getName()); //
    }

    @Test
    @DisplayName("카테고리 삭제 성공")
    void deleteCategory_Success() {
        // Given
        Long categoryId = 1L;
        Category categoryToDelete = Category.builder().name("DeleteCategory").build(); //
        try {
            java.lang.reflect.Field idField = Category.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(categoryToDelete, categoryId);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryToDelete)); //
        when(productRepository.findByCategoryWithPessimisticLock(categoryToDelete)).thenReturn(Arrays.asList()); // // 연관 상품 없음
        doNothing().when(categoryRepository).delete(categoryToDelete); //

        // When
        categoryService.deleteCategory(categoryId); //

        // Then
        verify(categoryRepository, times(1)).findById(categoryId); //
        verify(productRepository, times(1)).findByCategoryWithPessimisticLock(categoryToDelete); //
        verify(categoryRepository, times(1)).delete(categoryToDelete); //
    }

    @Test
    @DisplayName("카테고리 삭제 실패: 카테고리를 찾을 수 없음")
    void deleteCategory_NotFound_ThrowsException() {
        // Given
        Long categoryId = 99L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty()); //

        // When & Then
        assertThatThrownBy(() -> categoryService.deleteCategory(categoryId)) //
                .isInstanceOf(BaseException.class) //
                .hasMessageContaining("카테고리를 찾을 수 없습니다. ID: " + categoryId); //

        verify(categoryRepository, times(1)).findById(categoryId); //
        verify(productRepository, never()).findByCategoryWithPessimisticLock(any(Category.class)); //
        verify(categoryRepository, never()).delete(any(Category.class)); //
    }

    @Test
    @DisplayName("카테고리 삭제 시 연관된 상품이 있는 경우")
    void deleteCategory_WithAssociatedProducts_Success() {
        // Given
        Long categoryId = 1L;
        Category categoryToDelete = Category.builder()
                .name("CategoryWithProducts")
                .build();
        
        // ID 설정
        try {
            java.lang.reflect.Field idField = Category.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(categoryToDelete, categoryId);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        // Dummy Brand for Product
        Brand dummyBrand = Brand.builder()
                .name("DummyBrand")
                .build();
        try {
            java.lang.reflect.Field idField = Brand.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(dummyBrand, 100L);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        // Associated products
        Product product1 = Product.builder()
                .brand(dummyBrand)
                .category(categoryToDelete)
                .price(1000)
                .build();
        List<Product> associatedProducts = Arrays.asList(product1);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryToDelete));
        when(productRepository.findByCategoryWithPessimisticLock(categoryToDelete)).thenReturn(associatedProducts);

        // When
        categoryService.deleteCategory(categoryId);

        // Then
        verify(categoryRepository).findById(categoryId);
        verify(productRepository).findByCategoryWithPessimisticLock(categoryToDelete);
        verify(categoryRepository).delete(categoryToDelete);
    }
}