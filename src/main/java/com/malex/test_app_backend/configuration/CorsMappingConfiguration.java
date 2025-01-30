package com.malex.test_app_backend.configuration;

import static com.malex.test_app_backend.security.XAuthTokenAuthorizationRequestFilter.X_AUTH_TOKEN_HEADER;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class CorsMappingConfiguration implements WebMvcConfigurer {

  public static final String WEB_APP_USER_ATTRIBUTE_KEY = "webAppUser";

  public static final String API_URL_PATTERN = "/**";

  @Value("${cors.allowed.frontend.urls}")
  private String[] allowedFrontendUrls;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    log.info("CORS allowed URL mappings: {}", Arrays.toString(allowedFrontendUrls));
    registry
        .addMapping(API_URL_PATTERN)
        .allowedOrigins(allowedFrontendUrls)
        .allowedMethods(
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.OPTIONS.name())
        .allowedHeaders("*")
        // Set the list of response headers that an actual response might have and can be exposed.
        .exposedHeaders(X_AUTH_TOKEN_HEADER, HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE)
        .allowCredentials(true);
  }
}
