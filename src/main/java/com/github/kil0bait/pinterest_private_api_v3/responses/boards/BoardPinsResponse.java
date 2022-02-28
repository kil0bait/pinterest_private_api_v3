package com.github.kil0bait.pinterest_private_api_v3.responses.boards;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.kil0bait.pinterest_private_api_v3.models.pin.BoardPin;
import com.github.kil0bait.pinterest_private_api_v3.responses.PIResponse;

import java.util.List;

public class BoardPinsResponse extends PIResponse {
    @JsonProperty("data")
    private List<BoardPin> pins;

    public List<BoardPin> getPins() {
        return pins;
    }
}
