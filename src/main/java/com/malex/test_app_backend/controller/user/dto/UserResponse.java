package com.malex.test_app_backend.controller.user.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.malex.test_app_backend.controller.user.dto.info.UserInfoResponse;
import com.malex.test_app_backend.controller.user.dto.ref.UserRefResponse;

public record UserResponse(
    String id,
    Long userId,
    UserInfoResponse userInfo,
    List<UserRefResponse> references,
    LocalDateTime created,
    LocalDateTime updated) {}
