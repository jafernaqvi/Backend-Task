package net.grandcentrix.assessment.smartenergy.api.device.security;

import net.grandcentrix.assessment.smartenergy.domain.service.DeviceRepository;
import net.grandcentrix.assessment.smartenergy.domain.model.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * A device authenticates via basic authentication. This implementation of the {@link UserDetailsService} is configured
 * in the respective {@link DeviceSecurityConfig}.
 *
 * @see Device
 */
@Service
public class DeviceUserDetailsService implements UserDetailsService {

    private final DeviceRepository deviceRepository;

    public DeviceUserDetailsService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String macAddress) throws UsernameNotFoundException {
        return deviceRepository.findByMacAddress(macAddress).orElseThrow();
    }
}
