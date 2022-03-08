package com.github.kil0bait.pinterest_private_api_v3.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.kil0bait.pinterest_private_api_v3.models.PIBaseModel;

import java.io.Serial;
import java.io.Serializable;

public class PIResponse extends PIBaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 2612371600067807142L;

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
