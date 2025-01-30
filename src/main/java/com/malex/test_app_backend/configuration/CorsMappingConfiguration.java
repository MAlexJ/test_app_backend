package com.malex.test_app_backend.configuration;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class CorsMappingConfiguration implements WebMvcConfigurer {

  public static final String API_URL_PATTERN = "/**";

  @Value("${cors.allowed.frontend.urls}")
  private String[] allowedFrontendUrls;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    log.info("URL Cors configuration mappings - {}", Arrays.toString(allowedFrontendUrls));
    registry
        .addMapping(API_URL_PATTERN)
        .allowedOrigins(allowedFrontendUrls)
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        // Set the list of response headers that an actual response might have and can be exposed.
        .exposedHeaders("X-Auth-Token", "Authorization", "Content-Type")
        .allowCredentials(true);
  }
}
