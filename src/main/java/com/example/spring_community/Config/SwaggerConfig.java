package com.example.spring_community.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition (
        info = @Info(title="WEEK5: Spring Doc + Swagger를 통한 API 문서화",
                description = "Green의 스프링 부트를 이용한 커뮤니티 개발의 API 명세서입니다.",
                version = "v3")
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/**"};
        return GroupedOpenApi.builder()
                .group("greenAPI")
                .pathsToMatch(paths)
                .build();
    }
}
