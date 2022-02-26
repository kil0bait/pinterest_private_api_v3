package com.github.kil0bait.pinterest_private_api_v3.responses.feeds;

import com.github.kil0bait.pinterest_private_api_v3.models.pin.PinImage;
import com.github.kil0bait.pinterest_private_api_v3.responses.PIResponse;

import java.util.List;

public class FeedsResponse extends PIResponse {
    private List<PinImage> data;

    public List<PinImage> getData() {
        return data;
    }
}
