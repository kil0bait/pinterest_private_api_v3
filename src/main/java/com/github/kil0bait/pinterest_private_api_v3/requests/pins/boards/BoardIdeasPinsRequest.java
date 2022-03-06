package com.github.kil0bait.pinterest_private_api_v3.requests.pins.boards;

import com.github.kil0bait.pinterest_private_api_v3.requests.pins.AbstractPinsRequest;

public class BoardIdeasPinsRequest extends AbstractPinsRequest {
    private final String boardId;

    public BoardIdeasPinsRequest(String boardId) {
        this.boardId = boardId;
    }

    @Override
    public String path() {
        return "boards/" + boardId + "/ideas/feed/";
    }
}
