package net.grandcentrix.assessment.smartenergy.domain.model;

import net.grandcentrix.assessment.smartenergy.api.device.security.DeviceSecurityConfig;
import net.grandcentrix.assessment.smartenergy.api.device.security.DeviceUserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * The device represents a smart plug. It's macAddress is used a the username for http basic authentication.
 *
 * @see DeviceSecurityConfig
 * @see DeviceUserDetailsService
 */
@Entity
public class Device implements UserDetails {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id = UUID.randomUUID();

    private String macAddress;

    private String encodedPassword;

    @ManyToOne
    @JoinColumn(name ="linked_user")
    private AppUser appUser;

    @Column(name = "energy_consumption")
    private int energyConsumption;

    @Column(name = "energy_threshold")
    private int enerygyThreshold;

    public Device() {
    }

    public Device(String macAddress, String encodedPassword) {
        this.macAddress = macAddress;
        this.encodedPassword = encodedPassword;
    }

    @Override
    public String getPassword() {
        return encodedPassword;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return macAddress;
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

    public void setAppUser(AppUser appUser) { this.appUser = appUser; }

    public void setEnergyConsumption(int energyConsumption) { this.energyConsumption = energyConsumption; }

    public void setEnerygyThreshold(int enerygyThreshold) { this.enerygyThreshold = enerygyThreshold; }

    public AppUser getAppUser() { return appUser; }

    public int getEnergyConsumption() { return energyConsumption; }

    public int getEnerygyThreshold() { return enerygyThreshold; }
}
