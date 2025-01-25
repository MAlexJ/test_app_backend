package com.malex.test_app_backend.controller.user.dto;

import java.util.List;

public record UsersResponse(List<UserResponse> users, long total) {

  public UsersResponse(List<UserResponse> users) {
    this(users, users.size());
  }
}
