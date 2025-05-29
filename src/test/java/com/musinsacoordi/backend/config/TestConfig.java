package com.musinsacoordi.backend.config;

import com.musinsacoordi.backend.common.error.ErrorHandlingStrategy;
import com.musinsacoordi.backend.domain.brand.BrandRepository;
import com.musinsacoordi.backend.domain.category.CategoryRepository;
import com.musinsacoordi.backend.domain.product.ProductRepository;
import com.musinsacoordi.backend.domain.product.validation.ProductValidator;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;

/**
 * 테스트 설정 클래스
 *
 * 설계 방향 및 원칙:
 * - 테스트에 필요한 모든 Bean을 Mock으로 제공
 * - 실제 구현체 대신 Mock 객체를 사용하여 격리된 테스트 환경 구성
 *
 * 기술적 고려사항:
 * - @TestConfiguration을 사용하여 테스트 전용 설정 제공
 * - @Primary를 사용하여 실제 구현체 대신 Mock 객체가 주입되도록 보장
 *
 * 사용 시 고려사항:
 * - 테스트 클래스에서 @Import(TestConfig.class)로 설정 불러오기
 * - 필요한 경우 Mock 객체의 동작을 구체적으로 정의
 */
@TestConfiguration
@ComponentScan(basePackages = {
        "com.musinsacoordi.backend.common.dto",
        "com.musinsacoordi.backend.common.error",
        "com.musinsacoordi.backend.domain.product",
        "com.musinsacoordi.backend.domain.brand",
        "com.musinsacoordi.backend.domain.category"
})
public class TestConfig {

    @Bean
    @Primary
    public ProductRepository productRepository() {
        return Mockito.mock(ProductRepository.class);
    }

    @Bean
    @Primary
    public BrandRepository brandRepository() {
        return Mockito.mock(BrandRepository.class);
    }

    @Bean
    @Primary
    public CategoryRepository categoryRepository() {
        return Mockito.mock(CategoryRepository.class);
    }

    @Bean
    @Primary
    public ProductValidator productValidator() {
        return Mockito.mock(ProductValidator.class);
    }

    @Bean
    @Primary
    public ErrorHandlingStrategy errorHandlingStrategy() {
        return new ErrorHandlingStrategy();
    }
} 