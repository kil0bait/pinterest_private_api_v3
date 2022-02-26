package com.github.kil0bait.pinterest_private_api_v3.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PIPayload extends PIBaseModel {
    private String _csrftoken;
    private String id;
    private String _uid;
    private String _uuid;
    private String guid;
    private String device_id;
    private String phone_id;

    public String get_csrftoken() {
        return _csrftoken;
    }

    public void set_csrftoken(String _csrftoken) {
        this._csrftoken = _csrftoken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String get_uid() {
        return _uid;
    }

    public void set_uid(String _uid) {
        this._uid = _uid;
    }

    public String get_uuid() {
        return _uuid;
    }

    public void set_uuid(String _uuid) {
        this._uuid = _uuid;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getPhone_id() {
        return phone_id;
    }

    public void setPhone_id(String phone_id) {
        this.phone_id = phone_id;
    }
}
