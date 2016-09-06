package de.stytex.foobar.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by jxzhong on 9/5/16.
 */

@ConfigurationProperties(prefix = "http", ignoreUnknownFields = false)
public class HttpProperties {

    private final Cache cache = new Cache();

    public Cache getCache() {
        return cache;
    }

    public static class Cache {

        private int timeToLiveInDays = 1461;

        public int getTimeToLiveInDays() {
            return timeToLiveInDays;
        }

        public void setTimeToLiveInDays(int timeToLiveInDays) {
            this.timeToLiveInDays = timeToLiveInDays;
        }
    }
}

