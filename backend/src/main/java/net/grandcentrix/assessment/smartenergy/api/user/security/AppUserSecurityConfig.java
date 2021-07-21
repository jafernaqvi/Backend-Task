package net.grandcentrix.assessment.smartenergy.api.user.security;

import net.grandcentrix.assessment.smartenergy.api.WebPaths;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * The api that is used by the {@link net.grandcentrix.assessment.smartenergy.domain.model.AppUser} is behind the path
 * {@value WebPaths#USERS}. This configuration secures the endpoints with http basic authentication.
 */
@Configuration
@Order(1)
public class AppUserSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AppUserDetailsService appUserDetailsService;

    public AppUserSecurityConfig(AppUserDetailsService appUserDetailsService) {
        this.appUserDetailsService = appUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.requestMatchers(matchers -> matchers.antMatchers(WebPaths.USERS + "/**"))
                .httpBasic(config -> config.realmName("users"))
                .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(appUserDetailsService);
    }

}
