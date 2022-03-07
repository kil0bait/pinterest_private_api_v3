package com.github.kil0bait.pinterest_private_api_v3.requests.pins.search;

import com.github.kil0bait.pinterest_private_api_v3.PIClient;
import com.github.kil0bait.pinterest_private_api_v3.requests.pins.AbstractPinsRequest;
import com.github.kil0bait.pinterest_private_api_v3.responses.pins.PinsResponse;

public class SearchPinsRequest extends AbstractPinsRequest {
    private final String searchQuery;

    public SearchPinsRequest(String searchQuery) {
        this.searchQuery = searchQuery;
        addFields("pin.videos()",
                "video.video_list[V_HLSV4]",
                "video.duration",
                "video.id");
    }

    @Override
    public QueryParams queryParams(PIClient client) {
        QueryParams params = super.queryParams(client);
        params.addParam("query", searchQuery);
        return params;
    }

    @Override
    public String path() {
        return "search/pins/";
    }

    @Override
    public Class<PinsResponse> getResponseType() {
        return PinsResponse.class;
    }
}
