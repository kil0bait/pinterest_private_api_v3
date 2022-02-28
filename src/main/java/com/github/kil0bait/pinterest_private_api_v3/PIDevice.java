package com.github.kil0bait.pinterest_private_api_v3;

import java.io.Serial;
import java.io.Serializable;

public class PIDevice implements Serializable {
    @Serial
    private static final long serialVersionUID = -4348186002992919303L;
    private final String userAgent;
    private final String capabilities;
    private final String device;
    private final String cpu;
    private final String headerInfo;
    public static final String CAPABILITIES = "3brTvw==";

    public PIDevice(String formatted, String headerInfo) {
        this.userAgent = toUserAgent(formatted);
        this.capabilities = CAPABILITIES;
        String[] format = formatted.split("; ");
        this.device = format[0];
        this.cpu = format[1];
        this.headerInfo = headerInfo;
    }

    public static String toUserAgent(String formatted) {
        return String.format("Pinterest for Android Tablet/%s (%s)",
                PIConstants.APP_VERSION,
                formatted,
                PIConstants.LOCALE);
    }

    public static final PIDevice[] DEVICES = {
            new PIDevice("OnePlus5; 7.1.1", "ONEPLUS A5000")};


    public String toHeaderDevice() {
        return headerInfo;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getCapabilities() {
        return capabilities;
    }
}
