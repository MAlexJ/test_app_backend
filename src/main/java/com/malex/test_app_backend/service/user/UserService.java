package com.malex.test_app_backend.service.user;

import com.malex.test_app_backend.controller.user.dto.UserRequest;
import com.malex.test_app_backend.controller.user.dto.UserResponse;
import com.malex.test_app_backend.mapper.user.UserObjectMapper;
import com.malex.test_app_backend.repository.user.UserRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

  private final UserObjectMapper userObjectMapper;
  private final UserRepository userRepository;

  public Optional<UserResponse> findById(String id) {
    return userRepository.findById(id).map(userObjectMapper::entityToResponse);
  }

  public Optional<UserResponse> createUser(UserRequest userRequest) {
    return Optional.of(userObjectMapper.requestToEntity(userRequest))
        .map(userRepository::save)
        .map(userObjectMapper::entityToResponse);
  }
}
