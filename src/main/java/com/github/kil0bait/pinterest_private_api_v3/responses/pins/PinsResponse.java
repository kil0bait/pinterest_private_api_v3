package com.github.kil0bait.pinterest_private_api_v3.responses.pins;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.kil0bait.pinterest_private_api_v3.models.pin.Pin;
import com.github.kil0bait.pinterest_private_api_v3.responses.PIResponse;

import java.util.List;

public class PinsResponse extends PIResponse {
    @JsonProperty("data")
    private List<Pin> pins;

    public List<Pin> getPins() {
        return pins;
    }
}
