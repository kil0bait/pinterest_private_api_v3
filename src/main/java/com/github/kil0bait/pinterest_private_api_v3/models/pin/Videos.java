package com.github.kil0bait.pinterest_private_api_v3.models.pin;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Videos {
    @JsonProperty("id")
    String id;
    @JsonProperty("duration")
    Float duration;
    @JsonProperty("video_list")
    VideoList video_list;

}