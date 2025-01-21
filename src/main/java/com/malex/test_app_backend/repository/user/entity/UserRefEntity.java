package com.malex.test_app_backend.repository.user.entity;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "user_ref")
@TypeAlias("UserRef")
public class UserRefEntity {

  @MongoId private String id;

//  @Indexed(unique = true)
  private Long userId;

  @CreatedDate private LocalDateTime created;

  @LastModifiedDate private LocalDateTime updated;
}
