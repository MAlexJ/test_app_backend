package com.malex.test_app_backend.service.webApp;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class WebAppDataValidatorServiceTest {

  private static final Logger log = LoggerFactory.getLogger(WebAppDataValidatorServiceTest.class);

  private WebAppDataValidatorService service;

  @BeforeEach
  public void init() {
    service = new WebAppDataValidatorService();
    service.setBotToken("fakeBotToken");
    service.setSecretKeySuffix("TelegramWebAppData");
  }

  @Test
  void test() {
    String tgInitData = null;
    assertFalse(service.validateData(tgInitData));

    tgInitData = "";
    assertFalse(service.validateData(tgInitData));

    tgInitData = "text&hash=de7f6b26aadbd667a36d76d91969ecf6ffec70ffaa40b3e98d20555e2406bfbb";
    assertFalse(service.validateData(tgInitData));
  }

  @Test
  void testJava() throws Exception {
    String dataCheckString =
        "text&hash=de7f6b26aadbd667a36d76d91969ecf6ffec70ffaa40b3e98d20555e2406bfbb";
    String receivedHash = "de7f6b26aadbd667a36d76d91969ecf6ffec70ffaa40b3e98d20555e2406bfbb";

    boolean isValid = validateTelegramData(dataCheckString, receivedHash);
    assertFalse(isValid);
  }

  public boolean validateTelegramData(String dataCheckString, String receivedHash)
      throws Exception {
    // Create the secret key using HMAC_SHA256 with BOT_TOKEN and SECRET_KEY_SUFFIX
    byte[] secretKeyBytes =
        hmacSHA256(
            "fakeBotToken".getBytes(StandardCharsets.UTF_8),
            "TelegramWebAppData".getBytes(StandardCharsets.UTF_8));

    // Generate HMAC_SHA256 hash of the dataCheckString using the computed secret key
    byte[] dataHmac = hmacSHA256(secretKeyBytes, dataCheckString.getBytes(StandardCharsets.UTF_8));

    // Convert the resulting hash to a hex string
    String calculatedHashHex = bytesToHex(dataHmac);

    // Compare the calculated hash with the received hash
    log.info("calculatedHashHex: {}", calculatedHashHex);
    log.info("receivedHash: {}", receivedHash);
    return calculatedHashHex.equalsIgnoreCase(receivedHash);
  }

  private byte[] hmacSHA256(byte[] key, byte[] data) throws Exception {
    Mac mac = Mac.getInstance("HmacSHA256");
    SecretKeySpec secretKeySpec = new SecretKeySpec(key, "HmacSHA256");
    mac.init(secretKeySpec);
    return mac.doFinal(data);
  }

  private String bytesToHex(byte[] bytes) {
    return DatatypeConverter.printHexBinary(bytes).toLowerCase();
  }
}
