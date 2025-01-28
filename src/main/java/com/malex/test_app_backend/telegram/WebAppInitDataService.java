package com.malex.test_app_backend.telegram;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.malex.test_app_backend.telegram.model.WebAppUser;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
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
public class WebAppInitDataService {

  private static final String HMAC_ALGORITHM = "HmacSHA256";
  private static final String USER_KEY = "user";

  @Value("${webapp.telegram.secret.key}")
  private String telegramSecretKey;

  @Value("${webapp.telegram.bot.token}")
  private String telegramBotToken;

  private final ObjectMapper objectMapper = new ObjectMapper();

  public Optional<WebAppUser> validateTgInitData(String tgInitData) {
    try {
      Pair<String, String> result = parseInitData(tgInitData);
      String hash = result.getFirst();
      String initData = result.getSecond();
      byte[] secretKey = new HmacUtils(HMAC_ALGORITHM, telegramSecretKey).hmac(telegramBotToken);
      String initDataHash = new HmacUtils(HMAC_ALGORITHM, secretKey).hmacHex(initData);

      if (!initDataHash.equals(hash)) return Optional.empty();

      return Optional.ofNullable(parseQueryString(tgInitData).get(USER_KEY))
          .flatMap(this::parseJsonToWebAppUser);
    } catch (Exception e) {
      log.error(e.getMessage());
      return Optional.empty();
    }
  }

  /*
   * tgInitData is WebAppInitData
   * This object contains data that is transferred to the Mini App when it is opened.
   * It is empty if the Mini App was launched from a keyboard button or from inline mode.
   */
  boolean validate(String tgInitData) {
    try {
      Pair<String, String> result = parseInitData(tgInitData);
      String hash = result.getFirst();
      String initData = result.getSecond();
      byte[] secretKey = new HmacUtils(HMAC_ALGORITHM, telegramSecretKey).hmac(telegramBotToken);
      String initDataHash = new HmacUtils(HMAC_ALGORITHM, secretKey).hmacHex(initData);
      return initDataHash.equals(hash);
    } catch (Exception e) {
      log.error(e.getMessage());
      return false;
    }
  }

  private Optional<WebAppUser> parseJsonToWebAppUser(String json) {
    try {
      return Optional.of(objectMapper.readValue(json, WebAppUser.class));
    } catch (JsonProcessingException e) {
      log.error("Failed to parse WebAppUser JSON: {}", e.getMessage());
      return Optional.empty();
    }
  }

  private Pair<String, String> parseInitData(String telegramInitData) {
    Map<String, String> initData = parseQueryString(telegramInitData);
    initData = sortMap(initData);
    String hash = Objects.requireNonNull(initData.remove("hash"), "the `hash` parameter is null");
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
