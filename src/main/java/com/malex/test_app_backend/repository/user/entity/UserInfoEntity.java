package com.malex.test_app_backend.repository.user.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/*
 * Telegram WebAppUser representation
 * This object contains the data of the Mini App user.
 * link: https://core.telegram.org/bots/webapps#webappuser
 */
@Data
@Document(collection = "user_info")
@TypeAlias("UserInfo")
public class UserInfoEntity {

  @Id private String id;

  /*
   * A unique identifier for the user or bot.
   * This number may have more than 32 significant bits and some programming languages may have difficulty/silent
   * defects in interpreting it. It has at most 52 significant bits, so a 64-bit integer
   * or a double-precision float type is safe for storing this identifier.
   */
  @Indexed(unique = true)
  private Long userId;

  /*
   * First name of the user or bot.
   */
  @Field("first_name")
  private String firstName;

  /*
   * Optional. Last name of the user or bot.
   */
  @Field("last_name")
  private String lastName;

  /*
   * Optional. Username of the user or bot.
   */
  private String username;

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
