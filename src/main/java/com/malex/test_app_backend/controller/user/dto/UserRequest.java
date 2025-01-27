package com.malex.test_app_backend.controller.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record UserRequest(
    @Schema(name = "userId", type = "number", description = "telegram user id", example = "12345")
        Long userId,
//    String name,
//    String nickname,
    @Schema(name = "references", description = "user references")
        List<UserRefRequest> references) {}
