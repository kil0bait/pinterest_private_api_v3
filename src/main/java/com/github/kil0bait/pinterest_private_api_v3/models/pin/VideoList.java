package com.github.kil0bait.pinterest_private_api_v3.models.pin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;

public class VideoList implements Serializable {
    @Serial
    private static final long serialVersionUID = 8992931960349812682L;

    @JsonProperty("V_HLSV4")
    StreamVideo streamVideo;
}