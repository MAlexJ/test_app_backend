package com.malex.test_app_backend.telegram;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = {WebAppInitDataService.class})
@TestPropertySource("classpath:application.yaml")
class WebAppInitDataServiceTest {
  private static final Logger log = LoggerFactory.getLogger(WebAppInitDataServiceTest.class);

  @Value("${webapp.telegram.test.initData}")
  private String tgInitData;

  @Autowired private WebAppInitDataService webAppInitDataService;

  @Test
  void test() {
    if (skipTest()) {
      return;
    }
    logTest("SUCCESSFUL");
    assertTrue(webAppInitDataService.validate(tgInitData));
  }

  private void logTest(String successful) {
    log.info(successful);
  }

  @Test
  void testFail() {
    if (skipTest()) {
      return;
    }
    logTest("FAIL");
    String modifiedInitData = tgInitData.replace("Der", "der");
    assertFalse(webAppInitDataService.validate(modifiedInitData));
  }

  private boolean skipTest() {
    boolean skipTest = tgInitData == null || tgInitData.contains("${");
    if (skipTest) log.info("Skip test, the `tgInitData` is null");
    return skipTest;
  }
}
