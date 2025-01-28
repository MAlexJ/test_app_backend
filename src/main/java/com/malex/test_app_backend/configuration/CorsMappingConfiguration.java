package com.malex.test_app_backend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMappingConfiguration implements WebMvcConfigurer {

  @Value("${cors.allowed.frontend.urls}")
  private String[] allowedFrontendUrls;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/api/v1/**")
        .allowedOrigins(allowedFrontendUrls)
        .allowedMethods("GET", "POST", "PUT", "DELETE")
        .allowedHeaders("Content-Type", "Authorization")
        .allowCredentials(true);
  }
}
