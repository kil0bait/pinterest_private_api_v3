package com.github.kil0bait.pinterest_private_api_v3.requests.users;

import com.github.kil0bait.pinterest_private_api_v3.requests.PIGetRequest;
import com.github.kil0bait.pinterest_private_api_v3.responses.users.UserBoardResponse;

public class UserBoardRequest extends PIGetRequest<UserBoardResponse> {
    protected String userId;

    public UserBoardRequest(String userId) {
        this.userId = userId;
    }

    @Override
    public String path() {
        return "users/"+userId+"/boards/";
    }

    @Override
    public Class<UserBoardResponse> getResponseType() {
        return UserBoardResponse.class;
    }
}
