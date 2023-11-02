package com.zootopia.storeservice.common.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name("JWT")
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT"))
                )
                .info(new Info()
                        .title("마음길(HeartPath)")     /* 서비스 제목 */
                        .version("1.0.0")   /* 서비스 버전 */
                        .description("당신의 편지를 아날로그 감성으로 소중한 사람에게 전해보세요! \n\n  해당 swagger는 Letter 서비스와 Store 서비스와 관련된 API 명세서입니다."));    /* 서비스 설명 */
    }
}
