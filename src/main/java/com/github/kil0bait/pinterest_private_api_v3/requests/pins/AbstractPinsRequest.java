package com.github.kil0bait.pinterest_private_api_v3.requests.pins;

import com.github.kil0bait.pinterest_private_api_v3.PIClient;
import com.github.kil0bait.pinterest_private_api_v3.requests.PIGetRequest;
import com.github.kil0bait.pinterest_private_api_v3.responses.pins.PinsResponse;

import java.util.ArrayList;

public abstract class AbstractPinsRequest extends PIGetRequest<PinsResponse> {
    private Integer pinsAmount;

    @Override
    public QueryParams queryParams(PIClient client) {
        QueryParams params = super.queryParams(client);
        if (pinsAmount != null)
            params.addParam("page_size", pinsAmount.toString());
        return params;
    }

    @Override
    public Class<PinsResponse> getResponseType() {
        return PinsResponse.class;
    }

    public AbstractPinsRequest pinsAmount(Integer pinsAmount) {
        this.pinsAmount = pinsAmount;
        return this;
    }
}
