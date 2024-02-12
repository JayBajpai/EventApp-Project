package com.APIGateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APIGatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("UserService-MS", b -> b.path("/api/users/**")
                        .uri("http://localhost:8080"))
                .route("Authentication-MS", r -> r.path("/api/v1.0/authentication/**")
                        .uri("http://localhost:8081"))
                .route("EventAPI-MS", r -> r.path("/api/v1.0/events/**")
                        .uri("http://localhost:8082"))
                .route("FavoriteEvents-MS", r -> r.path("/api/wishlist/**")
                        .uri("http://localhost:8083"))
                .build();
    }
}