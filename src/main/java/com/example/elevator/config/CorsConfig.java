package com.example.elevator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Value("${elevator.cors.allowed-origins}")
    private String allowedOrigins;

    @Value("${elevator.cors.allowed-methods}")
    private String allowedMethods;

    @Value("${elevator.cors.allowed-headers}")
    private String allowedHeaders;

    @Value("${elevator.cors.allow-credentials}")
    private boolean allowCredentials;

    @Value("${elevator.cors.mapping}")
    private String corsMapping;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigins)
                        .allowedMethods(allowedMethods)
                        .allowedHeaders(allowedHeaders)
                        .allowCredentials(allowCredentials);
            }
        };
    }
}
