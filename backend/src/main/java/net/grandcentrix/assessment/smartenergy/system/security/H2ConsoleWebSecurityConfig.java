package net.grandcentrix.assessment.smartenergy.system.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * The default settings of spring security don't allow us to use the h2-console in modern browsers. This configuration
 * makes sure that it is usable.
 */
@Configuration
@Order(200)
public class H2ConsoleWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers(matcher -> matcher.antMatchers("/h2-console/**"))
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable();
    }
}
