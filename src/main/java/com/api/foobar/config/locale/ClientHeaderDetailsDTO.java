package com.api.foobar.config.locale;

import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by jxzhong on 9/6/16.
 */
public class ClientHeaderDetailsDTO {

    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9][1,36]*$")
    private String correlationId;
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9\\.-][1,10]+$")
    private String clientVersion;
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9][1,30]+$")
    private String clientId;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }
}

