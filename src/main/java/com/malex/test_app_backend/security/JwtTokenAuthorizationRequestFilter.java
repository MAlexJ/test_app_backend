package com.malex.test_app_backend.security;

import com.malex.test_app_backend.security.exception.ApplicationAuthorizationException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

@Slf4j
public class JwtTokenAuthorizationRequestFilter extends AbstractAuthorizationRequestFilter {

  private final String jwtSecretKey;

  public JwtTokenAuthorizationRequestFilter(String jwtSecretKey) {
    this.jwtSecretKey = jwtSecretKey;
  }

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
      var jwtToken = getJwtTokenFromAuthorizationHeader(request);
      verifyJwtToken(jwtToken);
      chain.doFilter(request, response);
    } catch (ApplicationAuthorizationException e) {
      sendUnauthorizedJsonResponse(request, response, e);
    }
  }

  private String getJwtTokenFromAuthorizationHeader(HttpServletRequest req) {
    return Optional.ofNullable(req.getHeader(HttpHeaders.AUTHORIZATION))
        .filter(header -> header.startsWith("Bearer "))
        .map(header -> header.substring(7))
        .orElseThrow(
            () ->
                new ApplicationAuthorizationException(
                    "Authorization JWT token is missing in request headers"));
  }

  private void verifyJwtToken(String jwtToken) {
    try {
      var signedJWT = SignedJWT.parse(jwtToken);
      var verifier = new MACVerifier(jwtSecretKey);
      if (!signedJWT.verify(verifier)) {
        throw new ApplicationAuthorizationException(
            "Invalid or missing JWT token. Please provide a valid JWT token in the Authorization header");
      }
      // Token is valid you can extract claims from the JWT token if needed
    } catch (ParseException | JOSEException e) {
      log.warn("Error parsing JWT token: {} error: {}", jwtToken, e.getMessage());
      throw new ApplicationAuthorizationException(
          "JWT token verification failed. Please check the token and try again");
    }
  }
}
