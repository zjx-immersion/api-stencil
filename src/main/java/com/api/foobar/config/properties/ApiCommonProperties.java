package com.api.foobar.config.properties;

import javax.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by jxzhong on 12/16/16.
 */
@ConfigurationProperties(prefix = "apicommon", ignoreUnknownFields = false)
public class ApiCommonProperties {
    private GlobalErrorResponse globalErrorResponse;

    public GlobalErrorResponse getGlobalErrorResponse() {
        return globalErrorResponse;
    }

    public static class GlobalErrorResponse {
        private boolean includeCause = true;

        public boolean isIncludeCause() {
            return includeCause;
        }

        public void setIncludeCause(boolean includeCause) {
            this.includeCause = includeCause;
        }

    }

    @PostConstruct
    public void postContruct() {
        if (globalErrorResponse == null) {
            globalErrorResponse = new GlobalErrorResponse();
        }
    }
}
