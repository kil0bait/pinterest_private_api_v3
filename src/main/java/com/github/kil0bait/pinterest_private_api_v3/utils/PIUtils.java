package com.github.kil0bait.pinterest_private_api_v3.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.kil0bait.pinterest_private_api_v3.PIConstants;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

public class PIUtils {
    static final ObjectMapper MAPPER = new ObjectMapper();
    static final ObjectWriter WRITER = MAPPER.writer();

    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.setSerializationInclusion(Include.NON_NULL);
    }

    private PIUtils() {
    }

    public static String generateSignature(String payload) {
        return "signed_body=SIGNATURE." + URLEncoder.encode(payload, StandardCharsets.UTF_8);

    }

    public static OkHttpClient.Builder defaultHttpClientBuilder() {
        return new OkHttpClient.Builder().cookieJar(new SerializableCookieJar());
    }

    public static String randomUuid() {
        return UUID.randomUUID().toString();
    }

    public static String objectToJson(Object obj) {
        try {
            return WRITER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T jsonToObject(String json, Class<T> view) throws JsonProcessingException {
        return MAPPER.readValue(json, view);
    }

    public static <T> T convertToView(Object o, Class<T> view) {
        return MAPPER.convertValue(o, view);
    }

    public static <T> T convertToView(Object o, TypeReference<T> type) {
        return MAPPER.convertValue(o, type);
    }

    public static Optional<String> getCookieValue(CookieJar jar, String key) {
        return jar.loadForRequest(HttpUrl.get(PIConstants.BASE_API_URL)).stream()
                .filter(cookie -> cookie.name().equals(key)).map(Cookie::value).findAny();
    }

    public static String truncate(String s) {
        return s != null ? s.substring(0, Math.min(100, s.length())) : s;
    }

}
