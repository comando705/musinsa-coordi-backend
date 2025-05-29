package com.musinsacoordi.backend.domain.product;

import com.musinsacoordi.backend.common.entity.BaseTimeEntity;
import com.musinsacoordi.backend.domain.brand.Brand;
import com.musinsacoordi.backend.domain.category.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"brand_id", "category_id"})
})
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // Category 객체를 참조

    @Column(nullable = false)
    private Integer price;

    @Builder
    public Product(Long id, Brand brand, Category category, Integer price) {
        this.id = id;
        this.brand = brand;
        this.category = category;
        this.price = price;
    }

    public void update(Brand brand, Category category, Integer price) {
        this.brand = brand;
        this.category = category;
        this.price = price;
    }

    public void updatePrice(Integer price) {
        this.price = price;
    }
}
