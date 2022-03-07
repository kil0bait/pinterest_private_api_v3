package com.github.kil0bait.pinterest_private_api_v3;

import com.github.kil0bait.pinterest_private_api_v3.requests.pins.boards.BoardIdeasPinsRequest;
import com.github.kil0bait.pinterest_private_api_v3.requests.pins.boards.BoardPinsRequest;
import com.github.kil0bait.pinterest_private_api_v3.requests.pins.feeds.FeedsHomePinsRequest;
import com.github.kil0bait.pinterest_private_api_v3.requests.pins.search.SearchPinsRequest;
import com.github.kil0bait.pinterest_private_api_v3.requests.users.UserBoardRequest;
import com.github.kil0bait.pinterest_private_api_v3.requests.users.UserFollowingRequest;
import com.github.kil0bait.pinterest_private_api_v3.responses.pins.PinsResponse;
import com.github.kil0bait.pinterest_private_api_v3.responses.users.UserBoardResponse;
import com.github.kil0bait.pinterest_private_api_v3.responses.users.UserFollowingResponse;
import org.jetbrains.annotations.Nullable;

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

    public CompletableFuture<PinsResponse> homeFeeds(@Nullable Integer pinsAmount,
                                                     @Nullable PinsResponse previous) {
        return new FeedsHomePinsRequest().pinsAmount(pinsAmount).previous(previous).execute(client);
    }

    public CompletableFuture<PinsResponse> boardPins(String boardId,
                                                     @Nullable Integer pinsAmount,
                                                     @Nullable PinsResponse previous) {
        return new BoardPinsRequest(boardId).pinsAmount(pinsAmount).previous(previous).execute(client);
    }

    public CompletableFuture<PinsResponse> boardIdeasFeedPins(String boardId,
                                                              @Nullable Integer pinsAmount,
                                                              @Nullable PinsResponse previous) {
        return new BoardIdeasPinsRequest(boardId).pinsAmount(pinsAmount).previous(previous).execute(client);
    }

    public CompletableFuture<PinsResponse> searchPins(String searchQuery,
                                                      @Nullable Integer pinsAmount,
                                                      @Nullable PinsResponse previous) {
        return new SearchPinsRequest(searchQuery).pinsAmount(pinsAmount).previous(previous).execute(client);
    }
}
