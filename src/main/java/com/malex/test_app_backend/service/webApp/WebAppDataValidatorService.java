package com.malex.test_app_backend.service.webApp;

import com.nimbusds.jose.util.Base64URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WebAppDataValidatorService {

  @Setter(AccessLevel.PACKAGE)
  @Value("${webapp.secret.key}")
  private String secretKeySuffix;

  @Setter(AccessLevel.PACKAGE)
  @Value("${webapp.bot.token}")
  private String botToken;

  /*
   * tgInitData is WebAppInitData
   * This object contains data that is transferred to the Mini App when it is opened.
   * It is empty if the Mini App was launched from a keyboard button or from inline mode.
   */
  public boolean validateData(String tgInitData) {
    try {
      String[] parts = tgInitData.split("&hash=");
      String hashReceived = parts[1];
      String dataWithoutHash = parts[0];
      String decodedData = URLDecoder.decode(dataWithoutHash, StandardCharsets.UTF_8);
      String secretKey = botToken + secretKeySuffix;

      String calculatedHash = calculateHMAC(decodedData, secretKey);
      log.info("Calculated hash: {}", calculatedHash);
      log.info("tgInitData hash: {}", hashReceived);
      return calculatedHash.equals(hashReceived);
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
      return false;
    } catch (Exception e) {
      log.error(e.getMessage());
      return false;
    }
  }

  private String calculateHMAC(String data, String key)
      throws NoSuchAlgorithmException, InvalidKeyException {
    byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
    SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "HmacSHA256");
    Mac mac = Mac.getInstance("HmacSHA256");
    mac.init(secretKeySpec);
    byte[] hmacBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    return Base64URL.encode(hmacBytes).toString(); // Encodes to Base64 URL-safe format
  }
}
