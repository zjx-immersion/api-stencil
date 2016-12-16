package com.api.foobar.healthcheck;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * Created by jxzhong on 10/18/16.
 */
public class HealthCheckException extends Exception {
    private final String environment;

    private final String endpoint;

    public HealthCheckException(String healthURL, Exception rce) {
        this(null, healthURL, rce);

    }

    public HealthCheckException(String environment, String endpoint, Exception e) {
        super(e.getMessage(), e);

        this.endpoint = endpoint;
        this.environment = environment;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public Throwable getRootCause() {
        return ExceptionUtils.getRootCause(getCause());
    }
}
