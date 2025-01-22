package com.malex.test_app_backend.controller.info.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import lombok.Getter;
import org.springframework.boot.info.BuildProperties;

@Getter
public class AppPropertiesResponse {

  private final String group;

  private final String version;

  private final String name;

  private final String artifact;

  private final Instant time;

  @JsonProperty("md5-hash")
  private final String md5Hash;

  public AppPropertiesResponse(BuildProperties buildProperties, String md5Hash) {
    this.group = buildProperties.getGroup();
    this.version = buildProperties.getVersion();
    this.name = buildProperties.getName();
    this.artifact = buildProperties.getArtifact();
    this.time = buildProperties.getTime();
    this.md5Hash = String.valueOf(buildProperties.get(md5Hash));
  }
}
