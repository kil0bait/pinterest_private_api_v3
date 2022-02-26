package com.github.kil0bait.pinterest_private_api_v3.requests.feeds;

import com.github.kil0bait.pinterest_private_api_v3.requests.PIGetRequest;
import com.github.kil0bait.pinterest_private_api_v3.responses.feeds.FeedsResponse;


public class FeedsRequest extends PIGetRequest<FeedsResponse> {

    @Override
    public String path() {
        return "feeds/home/";
    }

    @Override
    public Class<FeedsResponse> getResponseType() {
        return FeedsResponse.class;
    }
}
