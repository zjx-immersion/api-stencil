package de.stytex.foobar.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by jxzhong on 9/5/16.
 */

@ConfigurationProperties(prefix = "JwtSecurity", ignoreUnknownFields = false)
public class TokenSecurityProperties {

    private final Authentication authentication = new Authentication();

    public Authentication getAuthentication() {
        return authentication;
    }

    public static class Authentication {

        private final Jwt jwt = new Jwt();

        public Jwt getJwt() {
            return jwt;
        }

        public static class Jwt {

            private String secret;

            private long tokenValidityInSeconds = 1800;
            private long tokenValidityInSecondsForRememberMe = 2592000;

            public String getSecret() {
                return secret;
            }

            public void setSecret(String secret) {
                this.secret = secret;
            }

            public long getTokenValidityInSeconds() {
                return tokenValidityInSeconds;
            }

            public void setTokenValidityInSeconds(long tokenValidityInSeconds) {
                this.tokenValidityInSeconds = tokenValidityInSeconds;
            }

            public long getTokenValidityInSecondsForRememberMe() {
                return tokenValidityInSecondsForRememberMe;
            }

            public void setTokenValidityInSecondsForRememberMe(long tokenValidityInSecondsForRememberMe) {
                this.tokenValidityInSecondsForRememberMe = tokenValidityInSecondsForRememberMe;
            }
        }
    }
}
