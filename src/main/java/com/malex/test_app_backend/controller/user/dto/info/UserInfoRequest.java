package com.malex.test_app_backend.controller.user.dto.info;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserInfoRequest(String id,
                              Long userId,
                              @JsonProperty("first_name")
                              String firstName,
                              @JsonProperty("last_name")
                              String lastName,
                              String username,
                              String lang,
                              boolean isPremium,
                              Integer rank,
                              String profilePhoto) {}
