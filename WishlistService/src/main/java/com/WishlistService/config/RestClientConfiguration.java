package com.WishlistService.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfiguration {

    @Bean
    public RestTemplate getRestTemplate() {

        return new RestTemplate();
    }
    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearer-token",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                                .name("Authorization")))
                .info(new Info().title("WishlistService")
                        .description(" auth for Event app").version("1.0.0"))
                .addSecurityItem(new SecurityRequirement().addList("bearer-token"));
    }
}

