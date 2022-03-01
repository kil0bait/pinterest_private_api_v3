package com.github.kil0bait.pinterest_private_api_v3;

import com.github.kil0bait.pinterest_private_api_v3.requests.boards.BoardPinsRequest;
import com.github.kil0bait.pinterest_private_api_v3.requests.users.UserBoardRequest;
import com.github.kil0bait.pinterest_private_api_v3.requests.users.UserFollowingRequest;
import com.github.kil0bait.pinterest_private_api_v3.responses.boards.BoardPinsResponse;
import com.github.kil0bait.pinterest_private_api_v3.responses.users.UserBoardResponse;
import com.github.kil0bait.pinterest_private_api_v3.responses.users.UserFollowingResponse;

import java.util.concurrent.CompletableFuture;

public class PIClientActions {
    public static final String SELF = "me";
    private final PIClient client;

    public PIClientActions(PIClient client) {
        this.client = client;
    }

    public CompletableFuture<UserFollowingResponse> meFollowing() {
        return new UserFollowingRequest(SELF).execute(client);
    }

    public CompletableFuture<UserFollowingResponse> userFollowing(String userId) {
        return new UserFollowingRequest(userId).execute(client);
    }

    public CompletableFuture<UserBoardResponse> meBoards() {
        return new UserBoardRequest(SELF).execute(client);
    }

    public CompletableFuture<UserBoardResponse> userBoards(String userId) {
        return new UserBoardRequest(userId).execute(client);
    }

    public CompletableFuture<BoardPinsResponse> homeFeeds() {
        return BoardPinsRequest.builder()
                .feedType(BoardPinsRequest.FeedType.HOME_FEED)
                .pinsAmount(10)
                .build()
                .execute(client);
    }

    public CompletableFuture<BoardPinsResponse> boardPins(String boardId) {
        return BoardPinsRequest.builder()
                .boardId(boardId)
                .feedType(BoardPinsRequest.FeedType.BOARD_PINS)
                .pinsAmount(10)
                .build().execute(client);
    }

    public CompletableFuture<BoardPinsResponse> boardIdeasFeedPins(String boardId) {
        return BoardPinsRequest.builder()
                .boardId(boardId)
                .feedType(BoardPinsRequest.FeedType.BOARD_IDEAS_FEED)
                .pinsAmount(10)
                .build().execute(client);
    }

    public CompletableFuture<BoardPinsResponse> boardPinsWithAmount(String boardId, BoardPinsRequest.FeedType feedType, int itemCount) {
        return BoardPinsRequest.builder()
                .boardId(boardId)
                .feedType(feedType)
                .pinsAmount(itemCount)
                .build().execute(client);
    }
}
