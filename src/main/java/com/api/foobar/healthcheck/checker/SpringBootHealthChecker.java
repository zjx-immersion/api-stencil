package com.api.foobar.healthcheck.checker;

import com.api.foobar.healthcheck.HealthCheckException;
import com.api.foobar.healthcheck.HealthVO;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

/**
 * Created by jxzhong on 10/18/16.
 */
public class SpringBootHealthChecker {
    private RestOperations restOperations;

    public SpringBootHealthChecker(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    public Health check(String healthURL) throws HealthCheckException {
        return check(healthURL, new HttpHeaders());
    }

    private Health check(String healthURL, HttpHeaders httpHeaders) throws HealthCheckException {
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        Health health;

        try {
            ResponseEntity<HealthVO> healthVO =
                    restOperations.exchange(healthURL, HttpMethod.GET, httpEntity, HealthVO.class);
            health = Health.status(healthVO.getBody().getStatus()).build();
        } catch (RestClientException rce) {
            throw new HealthCheckException(healthURL, rce);
        }

        return health;
    }

}
