package com.api.foobar.security;

import com.api.foobar.config.constant.Constants;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

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
        return userName == null ? Constants.SYSTEM_ACCOUNT : userName;
    }
}
