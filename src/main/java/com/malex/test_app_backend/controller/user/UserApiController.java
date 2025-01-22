package com.malex.test_app_backend.controller.user;

import com.malex.test_app_backend.controller.user.dto.UserRequest;
import com.malex.test_app_backend.controller.user.dto.UserResponse;
import com.malex.test_app_backend.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserApiController {

  private final UserService userService;

  @Operation(description = "Returns user as per uuid")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Ok"),
        @ApiResponse(responseCode = "404", description = "Not found")
      })
  @GetMapping("/{uuid}")
  public ResponseEntity<UserResponse> findById(
      @PathVariable("uuid")
          @Parameter(name = "uuid", description = "User uuid", example = "678ec28a4b561b268832b726")
          String uuid) {

    if (uuid.equalsIgnoreCase("XXX")) {
      throw new RuntimeException("This is a test: " + LocalDateTime.now());
    }

    return userService
        .findById(uuid)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest) {
    return userService
        .createUser(userRequest)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.badRequest().build());
  }
}
