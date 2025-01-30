package com.malex.test_app_backend.security;

import com.malex.test_app_backend.security.exception.ApplicationAuthorizationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public abstract class AbstractAuthorizationRequestFilter extends OncePerRequestFilter {

  private static final String UNAUTHORIZED_ERROR_TEMPLATE =
      "{\"error\": \"Unauthorized\", \"message\": \"%s\"}";

  public void sendUnauthorizedJsonResponse(
      HttpServletRequest request, HttpServletResponse response, ApplicationAuthorizationException e)
      throws IOException {

    log.warn("Authorization error - {}", e.getMessage());

    /*
     * Set the Access-Control-Allow-Origin header based on the request's Origin header
     * This allows the client to receive error details from cross-origin requests
     */
    var allowOriginHeader = request.getHeader(HttpHeaders.ORIGIN);
    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, allowOriginHeader);

    /*
     * Send an unauthorized error response in JSON format
     */
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.getWriter().write(String.format(UNAUTHORIZED_ERROR_TEMPLATE, e.getMessage()));
    response.getWriter().flush();
  }
}
