package com.github.kil0bait.pinterest_private_api_v3.responses.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.kil0bait.pinterest_private_api_v3.models.users.User;
import com.github.kil0bait.pinterest_private_api_v3.responses.PIResponse;

import java.util.List;

public class UserFollowingResponse extends PIResponse {
    @JsonProperty("data")
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }
}
