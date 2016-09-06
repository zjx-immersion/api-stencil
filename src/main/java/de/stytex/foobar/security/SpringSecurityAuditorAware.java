package de.stytex.foobar.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import static de.stytex.foobar.config.constant.Constants.SYSTEM_ACCOUNT;

/**
 * Created by on 01.09.16.
 *
 * @author Jianxin Zhong
 *
 * Implementation of AuditorAware based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        String userName = SecurityUtils.getCurrentUserLogin();
        return userName == null ? SYSTEM_ACCOUNT : userName;
    }
}
