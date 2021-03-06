package com.api.foobar.config;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.LoggerContext;
import com.api.foobar.config.properties.LogStashProperties;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import net.logstash.logback.appender.LogstashSocketAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by on 01.09.16.
 *
 * @author Jianxin Zhong
 *
 */

@Configuration
@EnableConfigurationProperties(LogStashProperties.class)
public class LoggingConfiguration {

    private final Logger log = LoggerFactory.getLogger(LoggingConfiguration.class);

    private LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.port}")
    private String serverPort;

    @Value("${eureka.instance.instanceId}")
    private String instanceId;
    @Inject
    private LogStashProperties logStashProperties;

    @PostConstruct
    private void init() {
        if (logStashProperties.getLogstash().isEnabled()) {
            addLogstashAppender();
        }
    }

    public void addLogstashAppender() {
        log.info("Initializing Logstash logging");

        LogstashSocketAppender logstashAppender = new LogstashSocketAppender();
        logstashAppender.setName("LOGSTASH");
        logstashAppender.setContext(context);
        String customFields = "{\"app_name\":\"" + appName + "\",\"app_port\":\"" + serverPort + "\","
                + "\"instance_id\":\"" + instanceId + "\"}";

        // Set the Logstash appender config from JHipster properties
        logstashAppender.setSyslogHost(logStashProperties.getLogstash().getHost());
        logstashAppender.setPort(logStashProperties.getLogstash().getPort());
        logstashAppender.setCustomFields(customFields);
        logstashAppender.start();

        // Wrap the appender in an Async appender for performance
        AsyncAppender asyncLogstashAppender = new AsyncAppender();
        asyncLogstashAppender.setContext(context);
        asyncLogstashAppender.setName("ASYNC_LOGSTASH");
        asyncLogstashAppender.setQueueSize(logStashProperties.getLogstash().getQueueSize());
        asyncLogstashAppender.addAppender(logstashAppender);
        asyncLogstashAppender.start();

        context.getLogger("ROOT").addAppender(asyncLogstashAppender);
    }
}
