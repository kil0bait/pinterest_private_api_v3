package com.github.kil0bait.pinterest_private_api_v3.requests;

import com.github.kil0bait.pinterest_private_api_v3.PIClient;
import com.github.kil0bait.pinterest_private_api_v3.responses.PIResponse;
import okhttp3.Request;

public abstract class PIGetRequest<T extends PIResponse> extends PIRequest<T> {

    @Override
    public Request formRequest(PIClient client) {
        Request.Builder req = new Request.Builder()
                .url(this.formUrl(client));
        this.applyHeaders(client, req);

        return req.build();
    }
}
