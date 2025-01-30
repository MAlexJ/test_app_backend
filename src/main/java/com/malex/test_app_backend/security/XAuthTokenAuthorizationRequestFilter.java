package com.malex.test_app_backend.security;

import com.malex.test_app_backend.security.exception.ApplicationAuthorizationException;
import com.malex.test_app_backend.telegram.WebAppInitDataService;
import com.malex.test_app_backend.telegram.model.WebAppUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
public class XAuthTokenAuthorizationRequestFilter extends AbstractAuthorizationRequestFilter {

  private static final String X_AUTH_TOKEN_HEADER = "X-Auth-Token";
  public static final String WEB_APP_USER_ATTRIBUTE_KEY = "webAppUser";

  private final WebAppInitDataService service;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    /*
     * Allow preflight-requests (OPTIONS)
     */
    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
      chain.doFilter(request, response);
      return;
    }

    try {
      var webAppUser = verifyClientAuthorizationHeader(request);
      request.setAttribute(WEB_APP_USER_ATTRIBUTE_KEY, webAppUser);
      chain.doFilter(request, response);
    } catch (ApplicationAuthorizationException e) {
      log.warn("X-Auth-Token authorization error - {}", e.getMessage());
      writeUnauthorizedJsonResponse(response, e);
    }
  }

  private WebAppUser verifyClientAuthorizationHeader(HttpServletRequest request) {
    var thInitData =
        Optional.ofNullable(request.getHeader(X_AUTH_TOKEN_HEADER))
            .filter(StringUtils::hasText)
            .orElseThrow(
                () ->
                    new ApplicationAuthorizationException(
                        "The X-Auth-Token header is missing or empty in the request"));

    return service
        .validateTgInitData(thInitData)
        .orElseThrow(
            () ->
                new ApplicationAuthorizationException(
                    "The X-Auth-Token header contains invalid `tgInitData` data"));
  }
}
