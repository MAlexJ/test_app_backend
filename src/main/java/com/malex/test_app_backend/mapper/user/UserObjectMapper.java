package com.malex.test_app_backend.mapper.user;

import com.malex.test_app_backend.controller.user.dto.UserRequest;
import com.malex.test_app_backend.controller.user.dto.UserResponse;
import com.malex.test_app_backend.repository.user.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** MapStruct mapper: */
@Mapper(componentModel = "spring")
public interface UserObjectMapper {

  UserResponse entityToResponse(UserEntity entity);

  @Mapping(target = "id", ignore = true)
  UserEntity requestToEntity(UserRequest request);
}
