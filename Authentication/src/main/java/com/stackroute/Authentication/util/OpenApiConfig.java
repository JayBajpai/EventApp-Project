package com.stackroute.Authentication.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {
	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI()

				.info(new Info().title("AUTHENTICATION-MICROSERVICE")
						.description("Authentication for event app").version("1.0.0"));

	}
} 