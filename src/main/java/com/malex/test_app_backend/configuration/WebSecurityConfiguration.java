package com.malex.test_app_backend.configuration;

import com.malex.test_app_backend.security.JwtTokenAuthorizationRequestFilter;
import com.malex.test_app_backend.security.XAuthTokenAuthorizationRequestFilter;
import com.malex.test_app_backend.telegram.WebAppInitDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfiguration {

  /*
   * Webapp api
   */
  public static final String WEBAPP_API_URL_PATTERN = "/api/webapp/*";

  /*
   * Telegram bot api
   */
  public static final String BOT_API_URL_PATTERN = "/api/bot/*";

  /*
   * Info api
   */
  public static final String INFO_API_URL_PATTERN = "/api/info/*";

  @Value("${jwt.security.secret.key}")
  private String jwtSecret;

  private final WebAppInitDataService webAppInitDataService;

  @Bean
  @ConditionalOnProperty(prefix = "jwt.security", name = "enable", havingValue = "true")
  public FilterRegistrationBean<JwtTokenAuthorizationRequestFilter>
      jwtTokenAuthorizationRequestFilter() {
    var registrationBean = new FilterRegistrationBean<JwtTokenAuthorizationRequestFilter>();
    registrationBean.setFilter(new JwtTokenAuthorizationRequestFilter(jwtSecret));
    registrationBean.addUrlPatterns(BOT_API_URL_PATTERN, INFO_API_URL_PATTERN);
    return registrationBean;
  }

  @Bean
  public FilterRegistrationBean<XAuthTokenAuthorizationRequestFilter>
      clientAuthorizationTgInitDataRequestFilter() {
    var registrationBean = new FilterRegistrationBean<XAuthTokenAuthorizationRequestFilter>();
    registrationBean.setFilter(new XAuthTokenAuthorizationRequestFilter(webAppInitDataService));
    registrationBean.addUrlPatterns(WEBAPP_API_URL_PATTERN);
    return registrationBean;
  }
}
