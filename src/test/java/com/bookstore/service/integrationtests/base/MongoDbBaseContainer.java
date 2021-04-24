package com.bookstore.service.integrationtests.base;

import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;

@ContextConfiguration(initializers = MongoDbBaseContainer.Initializer.class)
@ActiveProfiles("test")
public class MongoDbBaseContainer extends GenericContainer<MongoDbBaseContainer> {

  private static MongoDbBaseContainer mongoDbContainer;

  private static final Logger logger = LoggerFactory.getLogger(MongoDbBaseContainer.class);

  static {
    mongoDbContainer = new MongoDbBaseContainer();
    mongoDbContainer.start();
  }

  public static final int MONGODB_PORT = 27017;
  public static final String DEFAULT_IMAGE_AND_TAG = "mongo:3.2.4";

  public MongoDbBaseContainer() {
    this(DEFAULT_IMAGE_AND_TAG);
  }

  public MongoDbBaseContainer(@NotNull String image) {
    super(image);
    addExposedPort(MONGODB_PORT);
  }

  @NotNull
  public Integer getPort() {
    return getMappedPort(MONGODB_PORT);
  }

  public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      logger.info("Mongodb properties are loading !!");

      TestPropertyValues values = TestPropertyValues.of(
          "spring.data.mongodb.host=" + mongoDbContainer.getContainerIpAddress(),
          "spring.data.mongodb.port=" + mongoDbContainer.getPort()

      );
      values.applyTo(configurableApplicationContext);
    }
  }
}