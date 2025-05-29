package com.musinsacoordi.backend.domain.product;

import com.musinsacoordi.backend.domain.brand.Brand;
import com.musinsacoordi.backend.domain.category.Category;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByBrand(Brand brand);

    List<Product> findByCategory(Category category);

    Optional<Product> findByBrandAndCategory(Brand brand, Category category);

    boolean existsByBrandIdAndCategoryId(Long brandId, Long categoryId);

    Optional<Product> findByBrandIdAndCategoryId(Long brandId, Long categoryId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.category = :category")
    List<Product> findByCategoryWithPessimisticLock(@Param("category") Category category);
}