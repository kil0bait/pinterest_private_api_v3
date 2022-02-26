package com.github.kil0bait.pinterest_private_api_v3.responses;

public class LoginResponse extends PIResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data {
        private String access_token;
        private String token_type;

        public String getAccess_token() {
            return access_token;
        }

        public String getToken_type() {
            return token_type;
        }
    }
}
