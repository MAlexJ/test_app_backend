package com.malex.test_app_backend.mapper.user;

import com.malex.test_app_backend.controller.webapp.user.dto.ref.UserRefRequest;
import com.malex.test_app_backend.controller.webapp.user.dto.ref.UserRefResponse;
import com.malex.test_app_backend.controller.webapp.user.dto.UserRequest;
import com.malex.test_app_backend.controller.webapp.user.dto.UserResponse;
import com.malex.test_app_backend.controller.webapp.user.dto.info.UserInfoRequest;
import com.malex.test_app_backend.controller.webapp.user.dto.info.UserInfoResponse;
import com.malex.test_app_backend.repository.user.entity.UserEntity;
import com.malex.test_app_backend.repository.user.entity.UserInfoEntity;
import com.malex.test_app_backend.repository.user.entity.UserRefEntity;
import java.util.Collections;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** MapStruct mapper: */
@Mapper(componentModel = "spring")
public interface UserObjectMapper {

  @Mapping(target = "references", source = "references")
  UserResponse entityToResponse(UserEntity entity);

  UserRefResponse entityToResponse(UserRefEntity entity);

  //  @Mapping(target = "isPremium", source = "isPremium")
  @Mapping(source = "premium", target = "isPremium")
  UserInfoResponse entityToResponse(UserInfoEntity entity);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "created", ignore = true)
  @Mapping(target = "updated", ignore = true)
  UserRefEntity entityToResponse(UserRefRequest request);

  /*
   * Many to One: Bidirectional Relationship
   */
  List<UserRefEntity> requestToEntity(List<UserRefRequest> request);

  @Mapping(target = "wallet", ignore = true)
  @Mapping(source = "isPremium", target = "premium")
  UserInfoEntity requestToEntity(UserInfoRequest request);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "created", ignore = true)
  @Mapping(target = "userInfo.userId", source = "userId")
  @Mapping(target = "userInfo.firstName", source = "firstName")
  @Mapping(target = "updated", ignore = true)
  @Mapping(
      target = "references",
      expression = "java(referencesOrDefault(request.references(), request.userId()))")
  UserEntity requestToEntity(UserRequest request);

  default List<UserRefEntity> referencesOrDefault(List<UserRefRequest> references, Long userId) {
    if (references == null || references.isEmpty()) {
      var defaultUserRefEntity = new UserRefEntity();
      defaultUserRefEntity.setUserId(userId);
      return Collections.singletonList(defaultUserRefEntity);
    }
    // Use the current value if it is already set
    return requestToEntity(references);
  }
}
