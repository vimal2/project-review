package com.revpay.gateway.config;

import com.revpay.gateway.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Auth Service - No JWT validation required
                .route("auth-service", r -> r
                        .path("/api/auth/**")
                        .uri("lb://auth-service"))

                // Security endpoints - Requires JWT validation
                .route("auth-service-security", r -> r
                        .path("/api/v1/security/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                        .uri("lb://auth-service"))

                // Card Service - Requires JWT validation
                .route("card-service", r -> r
                        .path("/api/v1/cards/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                        .uri("lb://card-service"))

                // Notification Service - Requires JWT validation
                .route("notification-service", r -> r
                        .path("/api/v1/notifications/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                        .uri("lb://notification-service"))

                // Wallet Service - Wallet endpoints - Requires JWT validation
                .route("wallet-service-wallet", r -> r
                        .path("/api/v1/wallet/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                        .uri("lb://wallet-service"))

                // Wallet Service - Transactions - Requires JWT validation
                .route("wallet-service-transactions", r -> r
                        .path("/api/v1/transactions/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                        .uri("lb://wallet-service"))

                // Wallet Service - Money Requests - Requires JWT validation
                .route("wallet-service-requests", r -> r
                        .path("/api/v1/requests/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                        .uri("lb://wallet-service"))

                // Business Service - Requires JWT validation
                .route("business-service", r -> r
                        .path("/api/v1/business/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                        .uri("lb://business-service"))

                .build();
    }
}
