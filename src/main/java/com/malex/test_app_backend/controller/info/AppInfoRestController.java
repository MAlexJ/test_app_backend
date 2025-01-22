package com.malex.test_app_backend.controller.info;

import com.malex.test_app_backend.controller.info.dto.AppPropertiesResponse;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/v1")
public class AppInfoRestController {

  private static final String MD5_HASH_PROJECT = "md5.hash.project";

  private final BuildProperties buildProperties;

  @GetMapping("/info")
  @Operation(
      description =
          "Provide build-related information such as group, artifact, build time and version of the project")
  public ResponseEntity<AppPropertiesResponse> appInfo() {
    var appProperties = new AppPropertiesResponse(buildProperties, MD5_HASH_PROJECT);
    return ResponseEntity.ok(appProperties);
  }
}
