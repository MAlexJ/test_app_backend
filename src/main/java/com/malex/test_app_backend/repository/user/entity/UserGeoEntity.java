package com.malex.test_app_backend.repository.user.entity;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "user_geo")
@TypeAlias("UserGeo")
public class UserGeoEntity {

  @MongoId
  private String id;

  @Indexed(unique = true)
  private Long userId;

  private String ip;

  private String country;

  private String region;

  private String city;

  private String timezone;

  private String eu;

  private List<Integer> ll;
}
