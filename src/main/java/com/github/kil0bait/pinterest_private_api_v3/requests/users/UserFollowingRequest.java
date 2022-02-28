package com.github.kil0bait.pinterest_private_api_v3.requests.users;

import com.github.kil0bait.pinterest_private_api_v3.requests.PIGetRequest;
import com.github.kil0bait.pinterest_private_api_v3.responses.users.UserFollowingResponse;

public class UserFollowingRequest extends PIGetRequest<UserFollowingResponse> {
    protected String userId;

    public UserFollowingRequest(String userId) {
        this.userId = userId;
    }

    @Override
    public String path() {
        return "users/"+userId+"/following/";
    }

    @Override
    public Class<UserFollowingResponse> getResponseType() {
        return UserFollowingResponse.class;
    }
}
