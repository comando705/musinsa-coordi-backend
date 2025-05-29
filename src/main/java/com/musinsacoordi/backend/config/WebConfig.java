package com.musinsacoordi.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 설정
 * 뷰 컨트롤러 및 기타 웹 관련 설정을 정의합니다.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 뷰 컨트롤러 등록
     * URL 경로와 뷰 이름을 명시적으로 매핑합니다.
     *
     * @param registry ViewControllerRegistry 인스턴스
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 브랜드, 카테고리, 상품 페이지에 대한 명시적 뷰 매핑
        registry.addViewController("/brands").setViewName("brands");
        registry.addViewController("/categories").setViewName("categories");
        registry.addViewController("/products").setViewName("products");
    }

    /**
     * 정적 리소스 핸들러 등록
     * CSS, JavaScript, 파비콘 등의 정적 리소스에 대한 경로를 매핑합니다.
     *
     * @param registry ResourceHandlerRegistry 인스턴스
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // CSS 파일 매핑
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");

        // JavaScript 파일 매핑
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");

        // 파비콘 매핑
        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations("classpath:/static/favicon.ico");
    }
}
