package com.api.foobar.config.filter;

import com.api.foobar.context.RequestContext;
import com.api.foobar.context.RequestContextHolder;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.FilterChain;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.api.foobar.config.constant.CommConstants.*;

/**
 * Created by jxzhong on 9/7/16.
 */
public class LoggingFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String, String> additionalHeaders = new HashedMap();

    public void includeHeader(String headerKey, String contextKey) {
        additionalHeaders.put(headerKey, contextKey);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        logger.debug("doFilter()");

        HttpServletRequest request = (HttpServletRequest) req;

        String correlationId = request.getHeader(CORRELATION_ID_FIELD_NAME);
        String clientId = request.getHeader(CLIENT_ID_FIELD_NAME);
        String clientVersion = request.getHeader(CLIENT_VERSION_FIELD_NAME);

        if (correlationId == null) {
            correlationId = UUID.randomUUID().toString();
        }

        RequestContext requestContext = new RequestContext(correlationId, clientId, clientVersion);

        if (!additionalHeaders.isEmpty()) {
            extractAdditionalHeadersIntoRequestContext(request, requestContext);
        }

        RequestContextHolder.set(requestContext);

        try {
            chain.doFilter(req, res);
        } finally {
//            RequestContextHolder.clear(); //todo : remove this clear logic, it seems the MDC will help manage the data in diff thread
        }
    }

    private void extractAdditionalHeadersIntoRequestContext(HttpServletRequest request, RequestContext requestContext) {
        additionalHeaders.keySet().stream().forEach((key) -> {
            String value = request.getHeader(key);

            if (StringUtils.isNotBlank(value)) {
                requestContext.setContext(additionalHeaders.get(key), value);
            }
        });
    }

    @Override
    public void destroy() {
        RequestContextHolder.clear();
    }
}
