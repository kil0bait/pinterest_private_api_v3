package com.github.kil0bait.pinterest_private_api_v3.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.kil0bait.pinterest_private_api_v3.PIClient;
import com.github.kil0bait.pinterest_private_api_v3.PIConstants;
import com.github.kil0bait.pinterest_private_api_v3.exceptions.PIResponseException;
import com.github.kil0bait.pinterest_private_api_v3.responses.PIResponse;
import com.github.kil0bait.pinterest_private_api_v3.utils.PIUtils;
import kotlin.Pair;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public abstract class PIRequest<T extends PIResponse> {
    private static final Logger log = LoggerFactory.getLogger(PIRequest.class);

    public abstract String path();

    public abstract Request formRequest(PIClient client);

    public abstract Class<T> getResponseType();

    public String apiPath() {
        return PIConstants.API_V1;
    }

    public String baseApiUrl() {
        return PIConstants.BASE_API_URL;
    }

    public String getQueryString(PIClient client) {
        return mapQueryString("client_id", PIConstants.CLIENT_ID,
                "timestamp", PIConstants.TIMESTAMP,
                "oauth_signature", PIConstants.OAUTH_SIGNATURE);
    }

    public HttpUrl formUrl(PIClient client) {
        return HttpUrl.parse(baseApiUrl() + apiPath() + path() + getQueryString(client));
    }

    public CompletableFuture<T> execute(PIClient client) {
        return client.sendRequest(this);
    }

    protected String mapQueryString(Object... strings) {
        StringBuilder builder = new StringBuilder("?");

        for (int i = 0; i < strings.length; i += 2) {
            if (i + 1 < strings.length && strings[i] != null && strings[i + 1] != null
                    && !strings[i].toString().isEmpty()
                    && !strings[i + 1].toString().isEmpty()) {
                builder.append(URLEncoder.encode(strings[i].toString(), StandardCharsets.UTF_8)).append("=")
                        .append(URLEncoder.encode(strings[i + 1].toString(), StandardCharsets.UTF_8)).append("&");
            }
        }

        return builder.substring(0, builder.length() - 1);
    }

    public T parseResponse(Pair<Response, String> response) throws PIResponseException {
        T piResponse = null;
        try {
            piResponse = parseResponse(response.getSecond());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        piResponse.setStatusCode(response.getFirst().code());
        if (!response.getFirst().isSuccessful() || (piResponse.getStatus() != null && piResponse.getStatus().equals("fail")))
            throw new PIResponseException(piResponse);

        return piResponse;
    }

    public T parseResponse(String json) throws JsonProcessingException {
        return parseResponse(json, getResponseType());
    }

    public <U> U parseResponse(String json, Class<U> type) throws JsonProcessingException {
        log.debug("{} parsing response : {}", apiPath() + path(), json);

        return PIUtils.jsonToObject(json, type);
    }

    protected Request.Builder applyHeaders(PIClient client, Request.Builder req) {
        req.addHeader("X-Pinterest-App-Type", "4");
        req.addHeader("Pinterest-App-Info", "version=7.33.0;build=7338040;environment=Release");
        req.addHeader("X-Pinterest-Appstate", "active");
        req.addHeader("X-Pinterest-Device", client.getDevice().toHeaderDevice());
        req.addHeader("User-Agent", client.getDevice().getUserAgent());
        req.addHeader("X-Pinterest-Device-Hardwareid", PIUtils.randomUuid());
        req.addHeader("X-Pinterest-Installid", PIUtils.randomUuid());
        req.addHeader("Accept-Language", PIConstants.LOCALE);
        req.addHeader("X-Pinterest-Carrier-Name", PIConstants.CARRIER);
        req.addHeader("X-Pinterest-Carrier-Mcc", "02");
        req.addHeader("X-Pinterest-Carrier-Mnc", "250");
        req.addHeader("X-Pinterest-Carrier-Radio-Technology", "LTE");

        Optional.ofNullable(client.getAuthToken())
                .ifPresent(auth -> req.addHeader("Authorization", auth));

        return req;
    }

}
