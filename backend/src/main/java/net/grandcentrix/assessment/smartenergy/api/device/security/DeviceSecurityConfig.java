package net.grandcentrix.assessment.smartenergy.api.device.security;

import net.grandcentrix.assessment.smartenergy.api.WebPaths;
import net.grandcentrix.assessment.smartenergy.domain.model.Device;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * A device communicates it energy consumption data to the backend. In order to be able to send it to the backend it
 * needs to authenticate. This configuration enables a http basic authentication strategy for the devices. The devices
 * itself identify by its mac address and a random generated password that the device receives upon registration.
 *
 * @see Device
 */
@Configuration
@Order(2)
public class DeviceSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DeviceUserDetailsService deviceUserDetailsService;

    public DeviceSecurityConfig(DeviceUserDetailsService deviceUserDetailsService) {
        this.deviceUserDetailsService = deviceUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.requestMatchers(matchers -> matchers.antMatchers(WebPaths.DEVICES + "/**", WebPaths.DEVICES))
                .httpBasic(config -> config.realmName("devices"))
                .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(deviceUserDetailsService);
    }

}
