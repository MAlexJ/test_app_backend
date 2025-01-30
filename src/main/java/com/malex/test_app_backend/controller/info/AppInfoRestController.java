package com.malex.test_app_backend.controller.info;

import com.malex.test_app_backend.controller.info.dto.AppPropertiesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Application API")
@RequiredArgsConstructor
@RequestMapping("/api/info")
public class AppInfoRestController {

  private static final String MD5_HASH_PROJECT = "md5.hash.project";

  private final BuildProperties buildProperties;

  @GetMapping("/build")
  @Operation(
      description =
          "Provide build-related information such as group, artifact, build time and version of the project")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Ok"),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema =
                        @Schema(
                            example =
                                " {\"error\": \"Unauthorized\", \"message\": \"JWT token not found in request headers\" }")))
      })
  public ResponseEntity<AppPropertiesResponse> appInfo() {
    var appProperties = new AppPropertiesResponse(buildProperties, MD5_HASH_PROJECT);
    return ResponseEntity.ok(appProperties);
  }
}
