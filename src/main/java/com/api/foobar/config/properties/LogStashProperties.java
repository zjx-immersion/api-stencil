package com.api.foobar.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by jxzhong on 9/5/16.
 */

@ConfigurationProperties(prefix = "logstash", ignoreUnknownFields = false)
public class LogStashProperties {

    private final Logstash logstash = new Logstash();

    public Logstash getLogstash() {
        return logstash;
    }

    public static class Logstash {

        private Boolean enabled = false;

        private String host = "localhost";

        private int port = 5000;

        private int queueSize = 512;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public int getQueueSize() {
            return queueSize;
        }

        public void setQueueSize(int queueSize) {
            this.queueSize = queueSize;
        }
    }
}
