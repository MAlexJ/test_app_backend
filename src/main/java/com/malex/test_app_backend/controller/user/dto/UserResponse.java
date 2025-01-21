package com.malex.test_app_backend.controller.user.dto;

import java.time.LocalDateTime;
import java.util.List;

public record UserResponse(
    String id,
    Long userId,
    List<UserRefResponse> references,
    LocalDateTime created,
    LocalDateTime updated) {}
