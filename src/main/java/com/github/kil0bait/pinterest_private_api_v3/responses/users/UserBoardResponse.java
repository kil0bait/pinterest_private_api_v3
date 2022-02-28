package com.github.kil0bait.pinterest_private_api_v3.responses.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.kil0bait.pinterest_private_api_v3.models.users.Board;
import com.github.kil0bait.pinterest_private_api_v3.responses.PIResponse;

import java.util.List;

public class UserBoardResponse extends PIResponse {
    @JsonProperty("data")
    private List<Board> boards;

    public List<Board> getBoards() {
        return boards;
    }
}
