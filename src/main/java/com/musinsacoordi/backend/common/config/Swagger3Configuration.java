package com.musinsacoordi.backend.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@SecurityScheme(
    name = "JWT Authentication",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer",
    in = SecuritySchemeIn.HEADER
)
@OpenAPIDefinition(
    info = @Info(
        title = "무신사 코디 백엔드 API",
        description = "무신사 코디 백엔드 API 명세서입니다. 브랜드, 카테고리, 상품 관리 API를 제공합니다.",
        version = "v1",
        contact = @Contact(
            name = "comando705",
            url = "https://github.com/comando705",
            email = "comando705@gmail.com"
        ),
        license = @License(name = "Apache 2.0", url = "https://springdoc.org/")),
        security = {
                @SecurityRequirement(name = "JWT Authentication")
        }
)
@Configuration
public class Swagger3Configuration {
}
