package com.github.kil0bait.pinterest_private_api_v3;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

public class PIDevice implements Serializable {
    @Serial
    private static final long serialVersionUID = -4348186002992919303L;
    private final String userAgent;
    private final String capabilities;
    private final Map<String, Object> deviceMap;

    public PIDevice(String userAgent, String capabilities, Map<String, Object> deviceMap) {
        this.userAgent = userAgent;
        this.capabilities = capabilities;
        this.deviceMap = deviceMap;
    }

    public String toHeaderDevice(){
        return "";
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getCapabilities() {
        return capabilities;
    }

    public Map<String, Object> getDeviceMap() {
        return deviceMap;
    }
}
