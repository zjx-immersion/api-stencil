package com.api.foobar.config;

import com.api.foobar.aop.logging.LoggingAspect;
import com.api.foobar.config.constant.Constants;
import org.springframework.context.annotation.*;

/**
 * Created by on 01.09.16.
 *
 * @author Jianxin Zhong
 *
 */

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    @Bean
    @Profile(Constants.SPRING_PROFILE_DEVELOPMENT)
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}
