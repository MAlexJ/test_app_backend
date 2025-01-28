package com.malex.test_app_backend.telegram.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/*
 * WebAppUser
 *
 * This object contains the data of the Mini App user.
 *
 * link: https://core.telegram.org/bots/webapps#webappuser
 */
@Data
public class WebAppUser {

  /*
   * Field: `id`
   * A unique identifier for the user or bot.
   * This number may have more than 32 significant bits
   */
  private Long id;

  /*
   * Field: `is_bot`
   * Optional.
   * True, if this user is a bot. Returns in the receiver field only.
   */
  @JsonProperty("is_bot")
  private boolean isBot;

  /*
   * Field: `first_name`
   * First name of the user or bot.
   */
  @JsonProperty("first_name")
  private String firstName;

  /*
   * Field: `last_name`
   * Optional
   * Last name of the user or bot.
   */
  @JsonProperty("last_name")
  private String lastName;

  /*
   * Field: `username`
   * Optional
   * Username of the user or bot.
   */
  @JsonProperty("username")
  private String username;

  /*
   * Field: `language_code`
   * Optional
   * IETF language tag of the user's language. Returns in user field only.
   */
  @JsonProperty("language_code")
  private String languageCode;

  /*
   * Field: `is_premium`
   * Optional.
   * True, if this user is a Telegram Premium user.
   */
  @JsonProperty("is_premium")
  private boolean isPremium;

  /*
   * Field: `added_to_attachment_menu`
   * Optional. True, if this user added the bot to the attachment menu
   */
  @JsonProperty("added_to_attachment_menu")
  private boolean addedToAttachmentMenu;

  /*
   * Field: `allows_write_to_pm`
   * Optional.
   * True, if this user allowed the bot to message them.
   */
  @JsonProperty("allows_write_to_pm")
  private boolean allowsWriteToPm;

  /*
   * Field: `photo_url`
   * Optional.
   * URL of the user’s profile photo. The photo can be in .jpeg or .svg formats
   */
  @JsonProperty("photo_url")
  private String photoUrl;
}
