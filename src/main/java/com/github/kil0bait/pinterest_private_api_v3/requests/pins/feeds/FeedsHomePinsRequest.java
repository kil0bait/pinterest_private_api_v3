package com.github.kil0bait.pinterest_private_api_v3.requests.pins.feeds;

import com.github.kil0bait.pinterest_private_api_v3.requests.pins.AbstractPinsRequest;
import com.github.kil0bait.pinterest_private_api_v3.responses.pins.PinsResponse;

public class FeedsHomePinsRequest extends AbstractPinsRequest {
    @Override
    public String path() {
        return "feeds/home/";
    }

    @Override
    public Class<PinsResponse> getResponseType() {
        return PinsResponse.class;
    }
}
