package com.malex.test_app_backend.repository.user.entity;

import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "user_info")
@TypeAlias("UserInfo")
public class UserInfoEntity {

  @MongoId private String id;

  private String name;

  private String nickname;

  private String lang;

  private boolean isPremium;

  private Integer rank;

  private String profilePhoto;

  private WalletInfo wallet;

  public record WalletInfo(
      String name,
      String address,
      String appName,
      String provider,
      String tonDns,
      String chain,
      String publicKey) {}
}
