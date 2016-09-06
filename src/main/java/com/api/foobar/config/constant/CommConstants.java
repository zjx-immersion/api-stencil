package com.api.foobar.config.constant;

/**
 * Created by jxzhong on 9/6/16.
 */
public final class CommConstants {

    public static final String CORRELATION_ID_FIELD_NAME = "X-Correlation-Id";
    public static final String CLIENT_ID_FIELD_NAME = "X-Client-Id";
    public static final String CLIENT_VERSION_FIELD_NAME = "X-Client-Version";
    public static final String ACCEPT_FIELD_NAME = "Accept";
    public static final String CONTENT_TYPE_FIELD_NAME = "Content-Type";

    public static final String HEALTH_CHECK = "HEALTHCHECK";

    public static final String REST_BASE_URL_PATTERN = "/api";

    public static final String APPLICATION_JSON_API_VALUE = "application/vnd.api+json";
    public static final String ACCEPT_APPLICATION_JSON_API_VALUE = "Accept" + APPLICATION_JSON_API_VALUE;



    private CommConstants() {
    }
}
