package de.stytex.foobar.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by jxzhong on 9/5/16.
 */
@ConfigurationProperties(prefix = "mail", ignoreUnknownFields = false)
public  class MailProperties {

    private String from = "foo@localhost";

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
