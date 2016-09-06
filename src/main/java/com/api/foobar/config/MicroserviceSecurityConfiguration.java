package com.api.foobar.config;

import com.api.foobar.security.jwt.JWTConfigurer;
import com.api.foobar.security.jwt.TokenProvider;
import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

/**
 * Created by on 01.09.16.
 *
 * @author Jianxin Zhong
 *
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class MicroserviceSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Inject
    private TokenProvider tokenProvider;

    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//            .antMatchers(HttpMethod.OPTIONS, "/**")
//            .antMatchers("/app/**/*.{js,html}")
//            .antMatchers("/bower_components/**")
//            .antMatchers("/i18n/**")
//            .antMatchers("/content/**")
//            .antMatchers("/swagger-ui/index.html")
//            .antMatchers("/test/**")
//            .antMatchers("/api/*") //for testing purposes
//            .antMatchers("/h2-console/**")
//            .antMatchers("metrics")
//            .antMatchers("health")
//            .antMatchers("info");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // @formatter:off

        http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin();

        // @formatter:on

//        http
//            .csrf()
//            .disable()
//            .headers()
//            .frameOptions()
//            .disable()
//        .and()
//            .sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        .and().httpBasic()
//        .and()
//            .authorizeRequests()
//            .antMatchers("/api/logs/**").hasAuthority(AuthoritiesConstants.ADMIN)
//            .antMatchers("/api/**").authenticated()
//            .antMatchers("/api/**").permitAll()         //for testing
//            .antMatchers("/metrics/**").permitAll()
//            .antMatchers("/health/**").permitAll()
//            .antMatchers("/trace/**").permitAll()
//            .antMatchers("/dump/**").permitAll()
//            .antMatchers("/shutdown/**").permitAll()
//            .antMatchers("/beans/**").permitAll()
//            .antMatchers("/configprops/**").permitAll()
//            .antMatchers("/info/**").permitAll()
//            .antMatchers("/autoconfig/**").permitAll()
//            .antMatchers("/env/**").permitAll()
//            .antMatchers("/mappings/**").permitAll()
////            .antMatchers("/liquibase/**").permitAll()
//            .antMatchers("/v2/api-docs/**").permitAll()
//            .antMatchers("/configuration/security").permitAll()
//            .antMatchers("/configuration/ui").permitAll()
//            .antMatchers("/protected/**").authenticated()
//            .and().formLogin();
//        .and()
//            .apply(securityConfigurerAdapter());

    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
}
