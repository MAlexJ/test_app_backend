package com.malex.test_app_backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = WavefrontProperties.Application.class)
class TestAppBackendApplicationTests {

  @Test
  void contextLoads() {}
}
