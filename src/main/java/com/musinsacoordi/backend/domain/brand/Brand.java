package com.musinsacoordi.backend.domain.brand;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.musinsacoordi.backend.common.entity.BaseTimeEntity;
import com.musinsacoordi.backend.domain.product.Product;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Brand extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    @Builder
    public Brand(String name) {
        this.name = name;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
