package net.grandcentrix.assessment.smartenergy.domain.service;

import net.grandcentrix.assessment.smartenergy.domain.model.AppUser;
import net.grandcentrix.assessment.smartenergy.domain.model.Device;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class LinkUserToDeviceService {

    private final DeviceRepository deviceRepository;

    public LinkUserToDeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public ImmutablePair<Integer,Device> linkUserToDevice(AppUser user, String deviceMacAddress) {
        Optional<Device> device = deviceRepository.findByMacAddress(deviceMacAddress);
        if (device.isPresent()){
            device.get().setAppUser(user);
            device.get().setEnerygyThreshold(100000);
            deviceRepository.save(device.get());
            return new ImmutablePair<>(200,device.get());
        } else {
            return new ImmutablePair<>(404, null);
        }
    }

    public ImmutablePair<Integer,Device> addThreshold(AppUser user, String deviceMacAddress, int threshold) {
        Optional<Device> device = deviceRepository.findByMacAddress(deviceMacAddress);
        if (device.isPresent()){
            if(device.get().getAppUser() == null || !user.getId().equals(device.get().getAppUser().getId())){
                return new ImmutablePair<>(403, null);
            }
            device.get().setEnerygyThreshold(threshold);
            deviceRepository.save(device.get());
            return new ImmutablePair<>(200,device.get());
        } else {
            return new ImmutablePair<>(404, null);
        }
    }

}
