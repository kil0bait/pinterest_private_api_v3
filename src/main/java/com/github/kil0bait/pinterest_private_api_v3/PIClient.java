package com.github.kil0bait.pinterest_private_api_v3;

import com.github.kil0bait.pinterest_private_api_v3.exceptions.PILoginException;
import com.github.kil0bait.pinterest_private_api_v3.exceptions.PIResponseException;
import com.github.kil0bait.pinterest_private_api_v3.models.PIPayload;
import com.github.kil0bait.pinterest_private_api_v3.requests.PIRequest;
import com.github.kil0bait.pinterest_private_api_v3.requests.login.LoginRequest;
import com.github.kil0bait.pinterest_private_api_v3.responses.LoginResponse;
import com.github.kil0bait.pinterest_private_api_v3.responses.PIResponse;
import com.github.kil0bait.pinterest_private_api_v3.utils.PIUtils;
import com.github.kil0bait.pinterest_private_api_v3.utils.SerializableCookieJar;
import com.github.kil0bait.pinterest_private_api_v3.utils.SerializeUtil;
import kotlin.Pair;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class PIClient implements Serializable {
    @Serial
    private static final long serialVersionUID = 7076937331908400905L;
    private static final Logger log = LoggerFactory.getLogger(PIClient.class);

    private final String username;
    private transient final String password;
    private boolean loggedIn = false;
    private String authToken;
    private PIDevice device = PIDevice.DEVICES[0];

    private transient OkHttpClient httpClient;
    private transient PIClientActions actions = new PIClientActions(this);

    public PIClient(String username, String password) {
        this(username, password, PIUtils.defaultHttpClientBuilder().build());
    }

    public PIClient(String username, String password, OkHttpClient client) {
        this.username = username;
        this.password = password;
        this.httpClient = client;
    }

    public CompletableFuture<LoginResponse> sendLoginRequest() {
        return new LoginRequest(username, password).execute(this)
                .thenApply((res) -> {
                    log.info("Successfully log in {}", username);
                    this.setLoggedInState(res);
                    this.authToken = res.getData().getToken_type() + " " + res.getData().getAccess_token();
                    return res;
                });
    }

    public <T extends PIResponse> CompletableFuture<T> sendRequest(@NotNull PIRequest<T> req) {
        CompletableFuture<Pair<Response, String>> responseFuture = new CompletableFuture<>();
        log.info("Sending request : {}", req.formUrl(this).toString());
        this.httpClient.newCall(req.formRequest(this)).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException exception) {
                responseFuture.completeExceptionally(exception);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response res) throws IOException {
                log.info("Response for {} : {}", call.request().url(), res.code());
                try (ResponseBody body = res.body()) {
                    responseFuture.complete(new Pair<>(res, body.string()));
                }
            }

        });

        return responseFuture
                .thenApply(res -> {
                    log.info("Response for {} with body (truncated) : {}",
                            res.getFirst().request().url(),
                            PIUtils.truncate(res.getSecond()));

                    try {
                        return req.parseResponse(res);
                    } catch (PIResponseException e) {
                        e.printStackTrace();
                        return null;
                    }
                });
    }

    private void setLoggedInState(LoginResponse state) {
        if (!state.getStatus().equals("success"))
            return;
        this.loggedIn = true;
    }

    public String getCsrfToken() {
        return PIUtils.getCookieValue(this.getHttpClient().cookieJar(), "csrftoken")
                .orElse("missing");
    }

    public PIPayload setPIPayloadDefaults(PIPayload load) {
        load.set_csrftoken(this.getCsrfToken());

        return load;
    }

    public static PIClient.Builder builder() {
        return new PIClient.Builder();
    }

    public static PIClient deserialize(File clientFile, File cookieFile)
            throws ClassNotFoundException, IOException {
        return deserialize(clientFile, cookieFile, PIUtils.defaultHttpClientBuilder());
    }

    public static PIClient deserialize(File clientFile, File cookieFile,
                                       OkHttpClient.Builder clientBuilder) throws ClassNotFoundException, IOException {
        PIClient client = SerializeUtil.deserialize(clientFile, PIClient.class);
        CookieJar jar = SerializeUtil.deserialize(cookieFile, SerializableCookieJar.class);

        client.httpClient = clientBuilder
                .cookieJar(jar)
                .build();

        client.actions = new PIClientActions(client);

        return client;
    }

    public void serialize(File clientFile, File cookieFile) throws IOException {
        SerializeUtil.serialize(this, clientFile);
        SerializeUtil.serialize(this.httpClient.cookieJar(), cookieFile);
    }

    @Serial
    private Object readResolve() throws ObjectStreamException {
        if (loggedIn)
            log.info("Successfully deserialized {}", username);
        return this;
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public PIDevice getDevice() {
        return device;
    }

    public PIClient setDevice(PIDevice device) {
        this.device = device;
        return this;
    }

    public String getAuthToken() {
        return authToken;
    }

    public PIClientActions actions() {
        return actions;
    }

    public static class Builder {
        private String username;
        private String password;
        private OkHttpClient client;
        private PIDevice device = PIDevice.DEVICES[0];

        private BiConsumer<PIClient, LoginResponse> onLogin = (client, login) -> {
        };

        public PIClient build() {
            return new PIClient(username, password, Optional.ofNullable(client)
                    .orElseGet(() -> PIUtils.defaultHttpClientBuilder().build())).setDevice(device);
        }

        public PIClient login() throws PILoginException {
            PIClient client = build();

            onLogin.accept(client, performLogin(client));

            return client;
        }

        private LoginResponse performLogin(PIClient client) throws PILoginException {
            LoginResponse response = client.sendLoginRequest().join();

            if (!client.isLoggedIn()) {
                throw new PILoginException(client, response);
            }

            return response;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder client(OkHttpClient client) {
            this.client = client;
            return this;
        }
    }
}
