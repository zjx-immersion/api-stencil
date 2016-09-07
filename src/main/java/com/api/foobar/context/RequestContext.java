package com.api.foobar.context;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Created by jxzhong on 9/7/16.
 */
public class RequestContext implements Serializable {
    private static final String CORRELATION_ID = "correlationID";
    private static final String CLIENT_ID = "clientID";
    private static final String CLIENT_VERSION = "clientVersion";

    private final Map<String, String> contextMap = new HashMap<>();

    public RequestContext(String correlationId, String clientId, String clientVersion) {
        contextMap.put(CORRELATION_ID, correlationId);
        contextMap.put(CLIENT_ID, clientId);
        contextMap.put(CLIENT_VERSION, clientVersion);
    }

    public String getCorrelationId() {
        return contextMap.get(CORRELATION_ID);
    }

    public String getClientId() {
        return contextMap.get(CLIENT_ID);
    }

    public String getClientVersion() {
        return contextMap.get(CLIENT_VERSION);
    }

    public String getContext(String key) {
        return contextMap.get(key);
    }

    public void setContext(String key, String value) {
        contextMap.put(key, value);
    }

    public Set<String> getContextKeys() {
        return contextMap.keySet();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
