package net.grandcentrix.assessment.smartenergy.domain.model;

import net.grandcentrix.assessment.smartenergy.api.user.security.AppUserDetailsService;
import net.grandcentrix.assessment.smartenergy.api.user.security.AppUserSecurityConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

/**
 * This class is representing the user of the mobile app. It implements the {@link UserDetails} interface to support basic authentication with the application.
 *
 * @see AppUserSecurityConfig
 * @see AppUserDetailsService
 */
@Entity
public class AppUser implements UserDetails {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id = UUID.randomUUID();

    private String username;

    private String encryptedPassword;

    public AppUser() { }

    public AppUser(String username, String encryptedPassword) {
        this.username = username;
        this.encryptedPassword = encryptedPassword;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return encryptedPassword;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
