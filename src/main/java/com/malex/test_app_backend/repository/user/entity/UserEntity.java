package com.malex.test_app_backend.repository.user.entity;

import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "user")
@TypeAlias("User")
public class UserEntity {

  @MongoId private String id;

  @Indexed(unique = true)
  private Long userId;
}
