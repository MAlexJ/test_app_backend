package com.malex.test_app_backend.controller.user;

import com.malex.test_app_backend.controller.user.dto.UserRequest;
import com.malex.test_app_backend.controller.user.dto.UserResponse;
import com.malex.test_app_backend.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@Tag(name = "User API")
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserApiController {

  private final UserService userService;

  @Operation(description = "Returns user as per tgInitData")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Ok"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
      })
  @GetMapping()
  public ResponseEntity<UserResponse> findOrCreateUser(
      @Parameter(name = "tgInitData", description = "Telegram.WebApp.initData value")
          @RequestParam(value = "tgInitData", required = false)
          Optional<String> tgInitDataOpt) {
    return tgInitDataOpt
        .filter(StringUtils::isNotBlank)
        .map(tgInitData -> ResponseEntity.ok(userService.findByIdOrCreateNewUser(tgInitData)))
        .orElseThrow(
            () ->
                new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Not Found `tgInitData` in request parameter"));
  }

  @PostMapping
  public ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest) {
    return userService
        .createUser(userRequest)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.badRequest().build());
  }
}
