package com.malex.test_app_backend.controller.webapp.user;

import static com.malex.test_app_backend.security.XAuthTokenAuthorizationRequestFilter.WEB_APP_USER_ATTRIBUTE_KEY;

import com.malex.test_app_backend.controller.webapp.user.dto.UserRequest;
import com.malex.test_app_backend.controller.webapp.user.dto.UserResponse;
import com.malex.test_app_backend.controller.webapp.user.dto.UsersResponse;
import com.malex.test_app_backend.security.exception.ApplicationAuthorizationException;
import com.malex.test_app_backend.service.user.UserService;
import com.malex.test_app_backend.telegram.model.WebAppUser;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "User API")
@RequestMapping("/api/webapp/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "X-Auth-Token-Auth")
public class UserApiController {

  private final UserService userService;

  @GetMapping("/{uuid}")
  public ResponseEntity<UserResponse> findById(
      HttpServletRequest request, @PathVariable("uuid") String uuid) {

    logWebAppUserAuthentication(request);

    return userService
        .findById(uuid)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<UsersResponse> findAll(HttpServletRequest request) {
    logWebAppUserAuthentication(request);
    return ResponseEntity.ok(userService.findAll());
  }

  @PostMapping
  public ResponseEntity<UserResponse> create(
      HttpServletRequest request, @RequestBody UserRequest userRequest) {
    logWebAppUserAuthentication(request);
    return userService
        .createUser(userRequest)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.badRequest().build());
  }

  private void logWebAppUserAuthentication(HttpServletRequest request) {
    var webAppUser =
        Optional.ofNullable(request.getAttribute(WEB_APP_USER_ATTRIBUTE_KEY))
            .map(WebAppUser.class::cast)
            .orElseThrow(
                () ->
                    new ApplicationAuthorizationException(
                        "Missing `webAppUser` in the request attributes"));
    log.info(
        "User- {} with 'id' - {} logged into the app",
        webAppUser.getUsername(),
        webAppUser.getId());
  }
}
