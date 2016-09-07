package com.api.foobar.config;

import javax.servlet.Filter;

import com.api.foobar.config.filter.LoggingFilter;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jxzhong on 9/7/16.
 */

@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean loggingFilterRegistrationBean() {
        return getFilterRegistrationBean(new LoggingFilter(), FilterRegistrationBean.HIGHEST_PRECEDENCE);
    }

    private FilterRegistrationBean getFilterRegistrationBean(Filter loggingFilter, int order) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

        filterRegistrationBean.setFilter(loggingFilter);
        filterRegistrationBean.setOrder(order);

        return filterRegistrationBean;
    }
}
