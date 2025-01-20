package com.malex.test_app_backend.repository.user.entity;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "user_balance")
@TypeAlias("UserBalance")
public class UserBalanceEntity {

  @MongoId private String id;

  @Indexed(unique = true)
  private Long userId;

  @Indexed private Integer lastBoonAmount;

  /*
   * in seconds (base speed without any bonuses)
   */
  @Indexed private Long miningSpeed;

  /*
   * Coefficient for bonus speed.
   * By default - 1
   */
  private Integer bonusSpeedMultiplier = 1;

  @CreatedDate private LocalDateTime created;
}
