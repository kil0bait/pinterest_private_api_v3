package com.github.kil0bait.pinterest_private_api_v3.exceptions;

import com.github.kil0bait.pinterest_private_api_v3.PIClient;
import com.github.kil0bait.pinterest_private_api_v3.responses.LoginResponse;

public class PILoginException extends PIResponseException {
    private final PIClient client;
    private final LoginResponse loginResponse;

    public PILoginException(PIClient client, LoginResponse body) {
        super(body);
        this.client = client;
        this.loginResponse = body;
    }

    public PIClient getClient() {
        return client;
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }
}
