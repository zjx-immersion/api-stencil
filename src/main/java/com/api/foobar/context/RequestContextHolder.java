package com.api.foobar.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Created by jxzhong on 9/7/16.
 */
public class RequestContextHolder {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestContextHolder.class);

    private static final ThreadLocal<RequestContext> REQUEST_CONTEXT_THREAD_LOCAL = new ThreadLocal();

    public RequestContextHolder() {
    }

    public static RequestContext get() {
        return REQUEST_CONTEXT_THREAD_LOCAL.get();
    }

    public static void set(RequestContext requestContext) {
        REQUEST_CONTEXT_THREAD_LOCAL.set(requestContext);

        if (requestContext != null) {
            setMDC(requestContext);
        }
    }

    public static void clear() {
        REQUEST_CONTEXT_THREAD_LOCAL.remove();
        clearMDC();
    }

    private static void clearMDC() {
        MDC.clear();
    }

    public static void setMDC(RequestContext context) {
        context.getContextKeys().stream().forEach(key -> MDC.put(key, context.getContext(key)));
    }
}
