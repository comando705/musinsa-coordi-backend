package com.musinsacoordi.backend.common.config;

import com.musinsacoordi.backend.common.error.ErrorCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.Properties;

/**
 * 에러 처리를 위한 설정 클래스
 * 일부 예외는 컨트롤러 어드바이스 대신 설정으로 처리합니다.
 */
@Configuration
public class ErrorHandlingConfig implements WebMvcConfigurer {

    /**
     * 특정 예외 유형에 대한 기본 에러 페이지를 설정합니다.
     * 이 설정은 REST API에는 적용되지 않고, 웹 페이지 요청에만 적용됩니다.
     */
    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        Properties mappings = new Properties();
        
        // 404 에러 페이지 매핑
        mappings.setProperty("org.springframework.web.servlet.resource.NoResourceFoundException", "error/404");
        mappings.setProperty("org.springframework.web.servlet.NoHandlerFoundException", "error/404");
        
        // 400 에러 페이지 매핑
        mappings.setProperty("org.springframework.web.bind.MissingServletRequestParameterException", "error/400");
        mappings.setProperty("org.springframework.web.method.annotation.MethodArgumentTypeMismatchException", "error/400");
        mappings.setProperty("org.springframework.http.converter.HttpMessageNotReadableException", "error/400");
        
        // 405 에러 페이지 매핑
        mappings.setProperty("org.springframework.web.HttpRequestMethodNotSupportedException", "error/405");
        
        // 500 에러 페이지 매핑
        mappings.setProperty("java.lang.Exception", "error/500");
        
        resolver.setExceptionMappings(mappings);
        resolver.setDefaultErrorView("error/default");
        resolver.setDefaultStatusCode(500);
        
        // 예외 이름을 모델에 추가
        resolver.setExceptionAttribute("exception");
        
        return resolver;
    }
}