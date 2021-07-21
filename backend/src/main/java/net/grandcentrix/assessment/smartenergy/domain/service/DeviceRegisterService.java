package net.grandcentrix.assessment.smartenergy.domain.service;

import net.grandcentrix.assessment.smartenergy.domain.model.Device;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * A {@link Device} needs to register itself in the backend to be able to send energy consumption data. This service
 * offers the functionality to do the registration via the devices mac address and generates a random password for
 * further authentication.
 */
@Service
public class DeviceRegisterService {

    public static final int PASSWORD_LENGTH = 18;

    private final DeviceRepository deviceRepository;
    private final PasswordEncoder passwordEncoder;

    public DeviceRegisterService(DeviceRepository deviceRepository, PasswordEncoder passwordEncoder) {
        this.deviceRepository = deviceRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * A device registers with its unique mac address. The backend generates a random password for the device for
     * further authentication.
     *
     * @param macAddress the mac address of the device that should be registered.
     * @return The password for the device.
     */
    public String register(String macAddress) {
        String plainPassword = RandomStringUtils.randomAlphanumeric(PASSWORD_LENGTH);
        String encodedPassword = passwordEncoder.encode(plainPassword);

        deviceRepository.save(new Device(macAddress, encodedPassword));
        return plainPassword;
    }

}
