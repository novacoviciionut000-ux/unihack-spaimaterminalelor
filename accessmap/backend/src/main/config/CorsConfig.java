package com.accessmap.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply CORS to all backend endpoints
                .allowedOrigins("http://localhost:5173") // Vite's default port. Change if Person D uses a different one.
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow standard REST actions
                .allowedHeaders("*") // Allow all headers (useful for JSON and custom auth tokens)
                .maxAge(3600); // Cache the preflight response for 1 hour to speed up the app
    }
}