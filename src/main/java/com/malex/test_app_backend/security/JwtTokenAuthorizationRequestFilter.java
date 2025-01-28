package com.malex.test_app_backend.security;

import com.malex.test_app_backend.security.exception.ClientAuthorizationException;
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
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JwtTokenAuthorizationRequestFilter extends OncePerRequestFilter {

  private final String jwtSecretKey;

  public JwtTokenAuthorizationRequestFilter(String jwtSecretKey) {
    this.jwtSecretKey = jwtSecretKey;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    try {
      var jwtToken = getJwtTokenFromAuthorizationHeader(request);
      verifyJwtToken(jwtToken);
      chain.doFilter(request, response);
    } catch (ClientAuthorizationException e) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
    }
  }

  private String getJwtTokenFromAuthorizationHeader(HttpServletRequest req) {
    return Optional.ofNullable(req.getHeader("Authorization"))
        .filter(header -> header.startsWith("Bearer "))
        .map(header -> header.substring(7))
        .orElseThrow(
            () -> new ClientAuthorizationException("JWT token not found in request headers"));
  }

  private void verifyJwtToken(String jwtToken) {
    try {
      var signedJWT = SignedJWT.parse(jwtToken);
      var verifier = new MACVerifier(jwtSecretKey);
      if (!signedJWT.verify(verifier)) {
        throw new ClientAuthorizationException(
            "Please provide a valid JWT token in the request headers");
      }
      // Token is valid you can extract claims from the JWT token if needed
    } catch (ParseException | JOSEException e) {
      log.error("Error parsing JWT token", e);
      throw new ClientAuthorizationException("JWT token verification failed");
    }
  }
}
