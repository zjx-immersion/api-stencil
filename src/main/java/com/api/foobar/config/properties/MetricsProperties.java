package com.api.foobar.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by jxzhong on 9/5/16.
 */

@ConfigurationProperties(prefix = "metrics", ignoreUnknownFields = false)
public  class MetricsProperties {

    private final Jmx jmx = new Jmx();

    private final Spark spark = new Spark();

    private final Graphite graphite = new Graphite();

    private final Logs logs = new Logs();

    public Jmx getJmx() {
        return jmx;
    }

    public Spark getSpark() {
        return spark;
    }

    public Graphite getGraphite() {
        return graphite;
    }

    public Logs getLogs() {
        return logs;
    }


    public static class Jmx {

        private boolean enabled = true;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    public static class Spark {

        private Boolean enabled = false;

        private String host = "localhost";

        private int port = 9999;

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
    }

    public static class Graphite {

        private Boolean enabled = false;

        private String host = "localhost";

        private int port = 2003;

        private String prefix = "foo";

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

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }
    }

    public static class Logs {

        private Boolean enabled = false;

        private long reportFrequency = 60;

        public long getReportFrequency() {
            return reportFrequency;
        }

        public void setReportFrequency(int reportFrequency) {
            this.reportFrequency = reportFrequency;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }
}
