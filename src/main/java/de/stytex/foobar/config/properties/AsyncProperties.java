package de.stytex.foobar.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by jxzhong on 9/5/16.
 */

@ConfigurationProperties(prefix = "anync", ignoreUnknownFields = false)
public class AsyncProperties {

    private int corePoolSize = 2;

    private int maxPoolSize = 50;

    private int queueCapacity = 10000;

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }
}
