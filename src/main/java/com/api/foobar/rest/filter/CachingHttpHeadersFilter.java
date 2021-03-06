package com.api.foobar.rest.filter;

import com.api.foobar.config.properties.HttpProperties;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;

/**
 * This filter is used in production, to put HTTP cache headers with a long (1 month) expiration time.
 */
public class CachingHttpHeadersFilter implements Filter {

    // We consider the last modified date is the start up time of the server
    private final static long LAST_MODIFIED = System.currentTimeMillis();

    private long cache_time_to_live = TimeUnit.DAYS.toMillis(1461L);

    private HttpProperties httpProperties;;

    public CachingHttpHeadersFilter(HttpProperties httpProperties) {
        this.httpProperties = httpProperties;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        cache_time_to_live = TimeUnit.DAYS.toMillis(httpProperties.getCache().getTimeToLiveInDays());
    }

    @Override
    public void destroy() {
        // Nothing to destroy
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse.setHeader("Cache-Control", "max-age=" + cache_time_to_live + ", public");
        httpResponse.setHeader("Pragma", "cache");

        // Setting Expires header, for proxy caching
        httpResponse.setDateHeader("Expires", cache_time_to_live + System.currentTimeMillis());

        // Setting the Last-Modified header, for browser caching
        httpResponse.setDateHeader("Last-Modified", LAST_MODIFIED);

        chain.doFilter(request, response);
    }
}
