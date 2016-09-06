package com.api.foobar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/**
 * Created by on 01.09.16.
 *
 * @author Jianxin Zhong
 *
 */
@Configuration
public class RestTemplateConfiguration {
    @Bean
    public RestOperations restTemplate() {
        return new RestTemplate();
    }
}
