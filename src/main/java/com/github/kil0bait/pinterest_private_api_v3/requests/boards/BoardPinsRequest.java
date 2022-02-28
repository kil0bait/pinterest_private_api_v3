package com.github.kil0bait.pinterest_private_api_v3.requests.boards;

import com.github.kil0bait.pinterest_private_api_v3.PIClient;
import com.github.kil0bait.pinterest_private_api_v3.requests.PIGetRequest;
import com.github.kil0bait.pinterest_private_api_v3.responses.boards.BoardPinsResponse;

import java.util.function.Function;

public class BoardPinsRequest extends PIGetRequest<BoardPinsResponse> {
    private String boardId;
    private int pinsAmount;
    private Function<String, String> path;

    public BoardPinsRequest(String boardId, int pinsAmount, Function<String, String> path) {
        this.boardId = boardId;
        this.pinsAmount = pinsAmount;
        this.path = path;
    }

    @Override
    public String path() {
        return path.apply(boardId);
    }

    @Override
    public String getQueryString(PIClient client) {
        return mapQueryString("page_size", pinsAmount);
    }

    @Override
    public Class<BoardPinsResponse> getResponseType() {
        return BoardPinsResponse.class;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String boardId = "";
        private int pinsAmount = 0;
        private Function<String, String> path = (undef) -> "";


        public BoardPinsRequest build() {
            return new BoardPinsRequest(boardId, pinsAmount, path);
        }

        public BoardPinsRequest.Builder boardId(String boardId) {
            this.boardId = boardId;
            return this;
        }

        public BoardPinsRequest.Builder pinsAmount(int pinsAmount) {
            this.pinsAmount = pinsAmount;
            return this;
        }

        public BoardPinsRequest.Builder feedType(FeedType feedType) {
            switch (feedType) {
                case BOARD_PINS -> path = (boardId) -> "boards/" + boardId + "/pins/";
                case BOARD_IDEAS_FEED -> path = (boardId) -> "boards/" + boardId + "/ideas/feed/";
                case HOME_FEED -> path = (undef) -> "feeds/home/";
            }
            return this;
        }
    }

    public enum FeedType {BOARD_PINS, BOARD_IDEAS_FEED, HOME_FEED}
}
