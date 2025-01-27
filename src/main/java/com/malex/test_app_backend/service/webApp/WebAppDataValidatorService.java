package com.malex.test_app_backend.service.webApp;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

/*
* Validating data received via the Mini App
*
* To validate data received via the Mini App, one should send the data from the Telegram.WebApp.initData field
* to the bot's backend.
* The data is a query string, which is composed of a series of field-value pairs.
*
* You can verify the integrity of the data received by comparing the received hash parameter with the hexadecimal
* representation of the HMAC-SHA-256 signature of the data-check-string with the secret key,
* which is the HMAC-SHA-256 signature of the bot's token with the constant string WebAppData used as a key.
 */
@Slf4j
@Service
public class WebAppDataValidatorService {

  @Setter(AccessLevel.PACKAGE)
  @Value("${webapp.telegram.secret.key}")
  private String telegramSecretKey;

  @Setter(AccessLevel.PACKAGE)
  @Value("${webapp.telegram.bot.token}")
  private String telegramBotToken;

  /*
   * tgInitData is WebAppInitData
   * This object contains data that is transferred to the Mini App when it is opened.
   * It is empty if the Mini App was launched from a keyboard button or from inline mode.
   */
  public boolean validateData(String tgInitData) {
    try {
      Pair<String, String> result = parseInitData(tgInitData);
      String hash = result.getFirst();
      String initData = result.getSecond();
      byte[] secretKey = new HmacUtils("HmacSHA256", telegramSecretKey).hmac(telegramBotToken);
      String initDataHash = new HmacUtils("HmacSHA256", secretKey).hmacHex(initData);

      return initDataHash.equals(hash);
    } catch (Exception e) {
      log.error(e.getMessage());
      return false;
    }
  }

  private Pair<String, String> parseInitData(String telegramInitData) {
    Map<String, String> initData = parseQueryString(telegramInitData);
    initData = sortMap(initData);
    String hash = initData.remove("hash");

    List<String> separatedData =
        initData.entrySet().stream().map((v) -> v.getKey() + "=" + v.getValue()).toList();
    return Pair.of(hash, String.join("\n", separatedData));
  }

  private Map<String, String> parseQueryString(String queryString) {
    Map<String, String> parameters = new TreeMap<>();
    String[] pairs = queryString.split("&");
    for (String pair : pairs) {
      int idx = pair.indexOf("=");
      String key =
          idx > 0 ? URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8) : pair;
      String value =
          idx > 0 && pair.length() > idx + 1
              ? URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8)
              : null;
      parameters.put(key, value);
    }
    return parameters;
  }

  private Map<String, String> sortMap(Map<String, String> map) {
    return new TreeMap<>(map);
  }
}
