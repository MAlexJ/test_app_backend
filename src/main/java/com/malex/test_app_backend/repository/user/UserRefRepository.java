package com.malex.test_app_backend.repository.user;

import com.malex.test_app_backend.repository.user.entity.UserRefEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRefRepository extends MongoRepository<UserRefEntity, String> {}
