package com.github.kil0bait.pinterest_private_api_v3.requests.login;

import com.github.kil0bait.pinterest_private_api_v3.PIClient;
import com.github.kil0bait.pinterest_private_api_v3.PIConstants;
import com.github.kil0bait.pinterest_private_api_v3.models.PIBaseModel;
import com.github.kil0bait.pinterest_private_api_v3.requests.PIPostRequest;
import com.github.kil0bait.pinterest_private_api_v3.responses.LoginResponse;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginRequest extends PIPostRequest<LoginResponse> {
    private final String username;
    private final String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected PIBaseModel getPayload(PIClient client) {
        return null;
    }

    @Override
    public String path() {
        return "login/";
    }

    @Override
    public String getQueryString(PIClient client) {
        return mapQueryString("client_id", PIConstants.CLIENT_ID,
                "timestamp", PIConstants.TIMESTAMP,
                "oauth_signature", PIConstants.OAUTH_SIGNATURE);
    }

    @Override
    public Class<LoginResponse> getResponseType() {
        return LoginResponse.class;
    }

    @Override
    protected RequestBody getRequestBody(PIClient client) {
        return RequestBody.create(mapQueryString("password",password,
                "username_or_email", username).substring(1), MediaType.parse("application/x-www-form-urlencoded"));
    }
}
