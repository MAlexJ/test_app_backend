package com.malex.test_app_backend.security;

import com.malex.test_app_backend.security.exception.ApplicationAuthorizationException;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

public abstract class AbstractAuthorizationRequestFilter extends OncePerRequestFilter {

  private static final String UNAUTHORIZED_ERROR_TEMPLATE =
      "{\"error\": \"Unauthorized\", \"message\": \"%s\"}";

  public void writeUnauthorizedJsonResponse(
      HttpServletResponse response, ApplicationAuthorizationException e) throws IOException {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.getWriter().write(String.format(UNAUTHORIZED_ERROR_TEMPLATE, e.getMessage()));
    response.getWriter().flush();
  }
}
