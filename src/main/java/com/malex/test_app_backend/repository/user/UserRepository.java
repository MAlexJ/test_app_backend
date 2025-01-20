package com.malex.test_app_backend.repository.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.malex.test_app_backend.repository.user.entity.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity, String> {}
