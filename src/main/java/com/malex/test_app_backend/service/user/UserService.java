package com.malex.test_app_backend.service.user;

import com.malex.test_app_backend.controller.user.dto.UserRequest;
import com.malex.test_app_backend.controller.user.dto.UserResponse;
import com.malex.test_app_backend.mapper.user.UserObjectMapper;
import com.malex.test_app_backend.repository.user.UserRefRepository;
import com.malex.test_app_backend.repository.user.UserRepository;
import com.malex.test_app_backend.repository.user.entity.UserEntity;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

  private final UserObjectMapper userObjectMapper;

  private final UserRepository userRepository;
  private final UserRefRepository userRefRepository;

  public Optional<UserResponse> findById(String id) {
    return userRepository.findById(id).map(userObjectMapper::entityToResponse);
  }

  @Transactional
  public Optional<UserResponse> createUser(UserRequest userRequest) {
    return Optional.of(userObjectMapper.requestToEntity(userRequest))
        .map(this::saveAllUserReverences)
        .map(userRepository::save)
        .map(userObjectMapper::entityToResponse);
  }

  /*
   * The MongoDB mapping framework does not handle cascading saves.
   * If you change an entity object that is referenced by a sub-entity object,
   * you must save the sub-entity object separately.
   * Calling save on the entity object does not automatically save the sub-entity objects in the sub-entities property.
   */
  private UserEntity saveAllUserReverences(UserEntity userEntity) {
    userRefRepository
        .saveAll(userEntity.getReferences())
        .forEach(ref -> log.trace("Saving user ref {} to user entity {}", ref.getId(), userEntity));
    return userEntity;
  }
}
