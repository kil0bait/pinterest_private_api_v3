package com.github.kil0bait.pinterest_private_api_v3.models.pin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;

public class StreamVideo implements Serializable {
    @Serial
    private static final long serialVersionUID = -7584468131191439838L;

    @JsonProperty("url")
    String url;
    @JsonProperty("thumbnail")
    String thumbnail;
}