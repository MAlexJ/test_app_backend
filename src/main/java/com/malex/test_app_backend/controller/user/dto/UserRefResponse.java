package com.malex.test_app_backend.controller.user.dto;

import java.time.LocalDateTime;

public record UserRefResponse(
    String id, Long userId, LocalDateTime created, LocalDateTime updated) {}
