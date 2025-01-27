package com.malex.test_app_backend.repository.user;

import com.malex.test_app_backend.repository.user.entity.UserInfoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserInfoRepository extends MongoRepository<UserInfoEntity, String> {}
