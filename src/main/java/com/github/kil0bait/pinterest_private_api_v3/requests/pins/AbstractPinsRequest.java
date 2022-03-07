package com.github.kil0bait.pinterest_private_api_v3.requests.pins;

import com.github.kil0bait.pinterest_private_api_v3.PIClient;
import com.github.kil0bait.pinterest_private_api_v3.requests.PIGetRequest;
import com.github.kil0bait.pinterest_private_api_v3.responses.pins.PinsResponse;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractPinsRequest extends PIGetRequest<PinsResponse> {
    protected Integer pinsAmount;
    protected String bookmark;
    protected List<String> fields = new ArrayList<>();

    public AbstractPinsRequest() {
        addFields("pin.id",
                "pin.type",
                "pin.image_large_url",
                "pin.grid_title",
                "pin.comment_count"
        );
    }

    @Override
    public QueryParams queryParams(PIClient client) {
        QueryParams params = super.queryParams(client);
        if (pinsAmount != null) {
            params.addParam("page_size", pinsAmount.toString());
            params.addParam("item_count", pinsAmount.toString());
        }
        if (bookmark != null)
            params.addParam("bookmark", bookmark);
        params.addParam("fields", String.join(",", fields));
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

    public AbstractPinsRequest previous(PinsResponse previous) {
        if (previous != null)
            this.bookmark = previous.getBookmark();
        return this;
    }

    public void addFields(String... fields) {
        this.fields.addAll(List.of(fields));
    }
}
