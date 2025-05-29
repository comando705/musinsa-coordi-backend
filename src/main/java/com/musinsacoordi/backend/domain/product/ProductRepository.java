package com.musinsacoordi.backend.domain.product;

import com.musinsacoordi.backend.domain.brand.Brand;
import com.musinsacoordi.backend.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByBrand(Brand brand);
    List<Product> findByCategory(Category category);
    Optional<Product> findByBrandAndCategory(Brand brand, Category category);
}