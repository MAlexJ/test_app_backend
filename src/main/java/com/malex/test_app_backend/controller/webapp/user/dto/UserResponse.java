package com.malex.test_app_backend.controller.webapp.user.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.malex.test_app_backend.controller.webapp.user.dto.info.UserInfoResponse;
import com.malex.test_app_backend.controller.webapp.user.dto.ref.UserRefResponse;

public record UserResponse(
    String id,
    Long userId,
    UserInfoResponse userInfo,
    List<UserRefResponse> references,
    LocalDateTime created,
    LocalDateTime updated) {}
