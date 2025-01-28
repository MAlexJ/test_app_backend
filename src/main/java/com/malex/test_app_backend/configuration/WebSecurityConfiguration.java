package com.malex.test_app_backend.configuration;

import com.malex.test_app_backend.security.JwtTokenAuthorizationRequestFilter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSecurityConfiguration {

  public static final String API_URL_PATTERN = "/api/v1/*";

  @Value("${jwt.security.secret.key}")
  private String jwtSecret;

  @Bean
  @ConditionalOnProperty(prefix = "jwt.security", name = "enable", havingValue = "true")
  public FilterRegistrationBean<JwtTokenAuthorizationRequestFilter>
  jwtTokenAuthorizationRequestFilter() {
    var registrationBean = new FilterRegistrationBean<JwtTokenAuthorizationRequestFilter>();
    registrationBean.setFilter(new JwtTokenAuthorizationRequestFilter(jwtSecret));
    registrationBean.addUrlPatterns(API_URL_PATTERN);
    return registrationBean;
  }
}
