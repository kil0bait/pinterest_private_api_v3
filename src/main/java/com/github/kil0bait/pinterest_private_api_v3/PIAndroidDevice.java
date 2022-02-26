package com.github.kil0bait.pinterest_private_api_v3;

import java.io.Serial;
import java.io.Serializable;

public class PIAndroidDevice extends PIDevice implements Serializable {
    @Serial
    private static final long serialVersionUID = 6927131116187398580L;
    private final String device;
    private final String cpu;
    private final String headerInfo;
    public static final String CAPABILITIES = "3brTvw==";

    public PIAndroidDevice(String formatted, String headerInfo) {
        super(toUserAgent(formatted), CAPABILITIES, null);
        String[] format = formatted.split("; ");
        this.device = format[0];
        this.cpu = format[1];
        this.headerInfo = headerInfo;
    }

    @Override
    public String toHeaderDevice() {
        return headerInfo;
    }

    public static String toUserAgent(String formatted) {
        return String.format("Pinterest for Android Tablet/%s (%s)",
                PIConstants.APP_VERSION,
                formatted,
                PIConstants.LOCALE);
    }

    public static final PIAndroidDevice[] DEVICES = {
            new PIAndroidDevice("OnePlus5; 7.1.1", "ONEPLUS A5000")};
}
