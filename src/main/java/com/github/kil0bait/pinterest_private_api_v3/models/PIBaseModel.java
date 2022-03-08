package com.github.kil0bait.pinterest_private_api_v3.models;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PIBaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = -8036294393830430410L;

    @JsonAnySetter
    private Map<String, Object> extra_properties = new HashMap<>();


    @JsonAnyGetter
    public Map<String, Object> getExtraProperties() {
        return this.extra_properties;
    }

    @JsonIgnore
    public Object get(String key) {
        return extra_properties.get(key);
    }

    @SuppressWarnings("unchecked")
    public <T extends PIBaseModel> T put(String key, Object val) {
        this.extra_properties.put(key, val);

        return (T) this;
    }
}
