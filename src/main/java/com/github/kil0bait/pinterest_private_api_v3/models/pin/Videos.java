package com.github.kil0bait.pinterest_private_api_v3.models.pin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;

public class Videos implements Serializable {
    @Serial
    private static final long serialVersionUID = -5888764989885497257L;

    @JsonProperty("id")
    String id;
    @JsonProperty("duration")
    Float duration;
    @JsonProperty("video_list")
    VideoList video_list;

}