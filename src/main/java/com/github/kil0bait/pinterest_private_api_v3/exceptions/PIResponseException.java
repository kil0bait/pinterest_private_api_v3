package com.github.kil0bait.pinterest_private_api_v3.exceptions;

import com.github.kil0bait.pinterest_private_api_v3.responses.PIResponse;
import com.github.kil0bait.pinterest_private_api_v3.utils.PIUtils;

import java.io.IOException;
import java.util.Optional;

public class PIResponseException extends IOException {
    private final PIResponse response;

    public PIResponseException(PIResponse response) {
        super(response.getMessage()+" : "+ Optional.of(response.getMessage_detailed()).orElse(""));
        this.response = response;
    }

    public PIResponse getResponse() {
        return response;
    }

    public static class PIFailedResponse extends PIResponse {
        private String status = "fail";
        private final String message;


        public PIFailedResponse(String message) {
            this.message = message;
        }

        public static PIResponse of(Throwable throwable) {
            if (throwable instanceof PIResponseException)
                return ((PIResponseException) throwable).getResponse();
            return new PIFailedResponse(throwable.toString());
        }

        public static <T> T of(Throwable throwable, Class<T> clazz) {
            return PIUtils.convertToView(of(throwable), clazz);
        }
    }

}
