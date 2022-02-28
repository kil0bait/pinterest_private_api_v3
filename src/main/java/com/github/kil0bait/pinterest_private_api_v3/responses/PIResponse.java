package com.github.kil0bait.pinterest_private_api_v3.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.kil0bait.pinterest_private_api_v3.models.PIBaseModel;

public class PIResponse extends PIBaseModel {
    private String status;
    private int code;
    @JsonIgnore
    private int statusCode;
    private String message;
    private String message_detailed;

    public String getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getMessage_detailed() {
        return message_detailed;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
