package net.grandcentrix.assessment.smartenergy.domain.service;

import net.grandcentrix.assessment.smartenergy.domain.model.AppUser;
import net.grandcentrix.assessment.smartenergy.domain.model.Device;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class EnergyConsumptionService {

    private final DeviceRepository deviceRepository;

    public EnergyConsumptionService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public ImmutablePair<Integer, Device> sendEnergyConsumption(String deviceMacAddress, int energyConsumed) {
        Optional<Device> device = deviceRepository.findByMacAddress(deviceMacAddress);
        if (device.isPresent()){
            // adding the energy consumption to old consumption
            device.get().setEnergyConsumption(device.get().getEnergyConsumption() + energyConsumed);
            // if threshold exceeded then send notification
            if(device.get().getEnergyConsumption() > device.get().getEnerygyThreshold()){
                System.out.println("Send user notification");
            }
            deviceRepository.save(device.get());
            return new ImmutablePair<>(200,device.get());
        } else {
            return new ImmutablePair<>(404, null);
        }
    }
}
