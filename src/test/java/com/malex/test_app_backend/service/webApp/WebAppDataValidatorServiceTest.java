package com.malex.test_app_backend.service.webApp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = WebAppDataValidatorService.class)
@TestPropertySource("classpath:application.yaml")
class WebAppDataValidatorServiceTest {

  private static final Logger log = LoggerFactory.getLogger(WebAppDataValidatorServiceTest.class);

  @Value("${webapp.telegram.test.initData}")
  private String tgInitData;

  @Autowired private WebAppDataValidatorService service;

  @Test
  void test() {
    if (tgInitData == null || tgInitData.contains("${")) {
      log.info("Skip test, the `tgInitData` is null");
      return;
    }
    assertTrue(service.validateData(tgInitData));
  }
}
