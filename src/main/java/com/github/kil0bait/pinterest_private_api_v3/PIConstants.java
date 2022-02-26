package com.github.kil0bait.pinterest_private_api_v3;

public class PIConstants {
    public static final String BASE_API_URL = "https://api.pinterest.com/";
    public static final String API_V1 = "v3/";
    public static final String APP_VERSION = "7.33.4";

    public static String CLIENT_ID = "1431602";
    public static String TIMESTAMP = "1645874257";
    public static String OAUTH_SIGNATURE = "72fbaa36a804ca4e7690dec9f70e2db1611cf86af915d4eb4248db09db7b8138";
    public static String LOCALE = "en_US";
    public static String CARRIER = "T-Mobile";

    public static void setClientId(String clientId) {
        CLIENT_ID = clientId;
    }

    public static void setTIMESTAMP(String TIMESTAMP) {
        PIConstants.TIMESTAMP = TIMESTAMP;
    }

    public static void setOauthSignature(String oauthSignature) {
        OAUTH_SIGNATURE = oauthSignature;
    }

    public static void setLOCALE(String LOCALE) {
        PIConstants.LOCALE = LOCALE;
    }

    public static void setCARRIER(String CARRIER) {
        PIConstants.CARRIER = CARRIER;
    }
}
