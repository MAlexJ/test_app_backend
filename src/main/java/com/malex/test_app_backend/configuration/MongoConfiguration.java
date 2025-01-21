package com.malex.test_app_backend.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Slf4j
@Configuration
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = "com.malex.test_app_backend.repository")
@RequiredArgsConstructor
public class MongoConfiguration extends AbstractMongoClientConfiguration {

  private final MongoProperties mongoProperties;

  @Bean
  public MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
    log.info("MongoDb configuration MongoTransactionManager");
    return new MongoTransactionManager(dbFactory);
  }

  @Override
  protected String getDatabaseName() {
    var databaseName =
        Objects.requireNonNull(mongoProperties.getDatabase(), "Database name is required");
    log.info("MongoDb configuration database properties: {}", databaseName);
    return mongoProperties.getDatabase();
  }

  @Override
  public MongoClient mongoClient() {
    var uri = Objects.requireNonNull(mongoProperties.getUri(), "Database URI is required");
    log.info("MongoDb configuration URI property: {}", uri);
    return MongoClients.create(uri);
  }

  @Override
  protected boolean autoIndexCreation() {
    return true;
  }
}
