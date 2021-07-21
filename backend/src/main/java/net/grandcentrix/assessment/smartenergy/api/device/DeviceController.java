package net.grandcentrix.assessment.smartenergy.api.device;

import net.grandcentrix.assessment.smartenergy.api.WebPaths;
import net.grandcentrix.assessment.smartenergy.domain.model.AppUser;
import net.grandcentrix.assessment.smartenergy.domain.model.Device;
import net.grandcentrix.assessment.smartenergy.domain.service.DeviceRegisterService;
import net.grandcentrix.assessment.smartenergy.domain.service.EnergyConsumptionService;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(WebPaths.DEVICES)
public class DeviceController {

    private final DeviceRegisterService deviceRegisterService;

    private final EnergyConsumptionService energyConsumptionService;

    public DeviceController(DeviceRegisterService deviceRegisterService, EnergyConsumptionService energyConsumptionService) {
        this.deviceRegisterService = deviceRegisterService;
        this.energyConsumptionService = energyConsumptionService;
    }

    /**
     * Registers a new device in the backend and returns its password to the client.
     *
     * @param request the clients mac address for registration.
     * @return the password for the device. The password is used by the devices to authenticate to the backend when sending energy consumption data.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceRegisterResponse register(@RequestBody DeviceRegisterRequest request) {
		String password = deviceRegisterService.register(request.getMacAddress());
		return new DeviceRegisterResponse(password);
    }

    /**
     * updates the energy consumed by the device
     *
     * @param deviceMacAddress mac address of the device
     * @param energyConsumed energy consumed by the device
     * @return
     */
    @PutMapping("send_energy_consumption/{deviceMacAddress}/{energyConsumed}")
    @ResponseStatus(HttpStatus.CREATED)
    public Object sendEnergyConsumption(@PathVariable String deviceMacAddress, @PathVariable int energyConsumed) {
        ImmutablePair<Integer, Device> servRes = energyConsumptionService.sendEnergyConsumption(deviceMacAddress,energyConsumed);
        if(servRes.getKey().equals(200)) {
            return new ResponseEntity<>(servRes.getValue().getPassword(), HttpStatus.OK);
        } else if (servRes.getKey().equals(404)){
            return new ResponseEntity("Device not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity("Something went wrong...", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
