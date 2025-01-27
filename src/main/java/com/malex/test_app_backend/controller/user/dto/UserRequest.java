package com.malex.test_app_backend.controller.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.malex.test_app_backend.controller.user.dto.info.UserInfoRequest;
import com.malex.test_app_backend.controller.user.dto.ref.UserRefRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record UserRequest(
    /*
     * userId
     */
    @Schema(name = "userId", type = "number", description = "telegram user id", example = "12345")
        Long userId,

    /*
     * userInfo
     */
    @Schema(name = "userInfo", type = "object", description = "user info") UserInfoRequest userInfo,

    /*
     * first_name
     */
    @JsonProperty("first_name")
        @Schema(
            name = "first_name",
            type = "string",
            description = "First name of the user or bot.")
        String firstName,

    /*
     * references
     */
    @Schema(name = "references", type = "object", description = "user references")
        List<UserRefRequest> references) {}
