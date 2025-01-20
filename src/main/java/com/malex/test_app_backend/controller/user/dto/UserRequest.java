package com.malex.test_app_backend.controller.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserRequest(@Schema(name = "User id", example = "12345") Long userId) {}
