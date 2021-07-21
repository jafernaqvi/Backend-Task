package net.grandcentrix.assessment.smartenergy.api.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import net.grandcentrix.assessment.smartenergy.api.WebPaths;
import net.grandcentrix.assessment.smartenergy.api.device.DeviceRegisterResponse;
import net.grandcentrix.assessment.smartenergy.domain.model.Device;
import net.grandcentrix.assessment.smartenergy.domain.service.LinkUserToDeviceService;
import net.grandcentrix.assessment.smartenergy.system.openapi.OpenApiConfig;
import net.grandcentrix.assessment.smartenergy.domain.model.AppUser;
import net.grandcentrix.assessment.smartenergy.domain.service.CreateUserService;
import net.grandcentrix.assessment.smartenergy.domain.service.GetUserService;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(WebPaths.USERS)
public class AppUserController {

    private final CreateUserService createUserService;

    private final LinkUserToDeviceService linkUserToDeviceService;

    private final GetUserService getUserService;

    public AppUserController(CreateUserService createUserService, LinkUserToDeviceService linkUserToDeviceService, GetUserService getUserService) {
        this.createUserService = createUserService;
        this.linkUserToDeviceService = linkUserToDeviceService;
        this.getUserService = getUserService;
    }

    /**
     * Creates a new user with the given name and password. It is not allowed to create a user with a username which is already present. The password of a user must be an empty string.
     *
     * @param request The request body with the relevant information to create the new user
     * @return A 201 with the location of the created user.
     */
    @PostMapping
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest request) throws Exception {
        ImmutablePair<Integer,AppUser> servRes = createUserService.createUser(request.getName(), request.getPassword());
        ResponseEntity responseEntity;
        if(servRes.getKey().equals(200)) {
            URI location = MvcUriComponentsBuilder
                    .fromMethodName(AppUserController.class, "getUser", servRes.getValue().getId())
                    .buildAndExpand(servRes.getValue().getId())
                    .toUri();
            responseEntity = ResponseEntity.created(location).build();
        } else if (servRes.getKey().equals(400)) {
            responseEntity = new ResponseEntity("Bad Argument", HttpStatus.BAD_REQUEST);
        } else if (servRes.getKey().equals(409)) {
            responseEntity = new ResponseEntity("Resource Already Exists", HttpStatus.CONFLICT);
        } else {
            responseEntity = new ResponseEntity("Something went wrong...", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * Get the information about the user. Only the user itself is allowed to see his own information.
     *
     * @param userId The id of the user to get
     * @return The public information of the user
     */
    @GetMapping("{userId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(security = @SecurityRequirement(name = OpenApiConfig.USER_AUTH))
    public Object getUser(@PathVariable UUID userId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<AppUser> user = getUserService.getUser(userId);
        if(!user.isPresent() || !user.get().getUsername().equals(authentication.getName())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else{
            return GetUserResponse.of(user.orElseThrow());
        }

    }

    /**
     * link the device to user
     *
     * @param userId The id of the user to get
     * @param deviceMacAddress mac address of the device to link
     * @return The public information of the user
     */
    @PutMapping("link_device/{userId}/{deviceMacAddress}")
    @PreAuthorize("isAuthenticated()")
    @Operation(security = @SecurityRequirement(name = OpenApiConfig.USER_AUTH))
    public Object linkDevice(@PathVariable UUID userId, @PathVariable String deviceMacAddress) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<AppUser> user = getUserService.getUser(userId);
        if(!user.isPresent() || !user.get().getUsername().equals(authentication.getName())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else{
            ImmutablePair<Integer, Device> servRes = linkUserToDeviceService.linkUserToDevice(user.get(),deviceMacAddress);
            if(servRes.getKey().equals(200)) {
                return new ResponseEntity<>("You have been linked to device : " + deviceMacAddress ,HttpStatus.OK);
            } else if (servRes.getKey().equals(404)) {
                return new ResponseEntity("Device not found", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity("Something went wrong...", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Adds energy threshold to linked device
     *
     * @param userId The id of the user to get
     * @param deviceMacAddress mac address of the device to link
     * @return The public information of the user
     */
    @PutMapping("add_threshold/{userId}/{deviceMacAddress}/{energyThreshold}")
    @PreAuthorize("isAuthenticated()")
    @Operation(security = @SecurityRequirement(name = OpenApiConfig.USER_AUTH))
    public Object addThreshold(@PathVariable UUID userId, @PathVariable String deviceMacAddress, @PathVariable int energyThreshold) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<AppUser> user = getUserService.getUser(userId);
        if(!user.isPresent() || !user.get().getUsername().equals(authentication.getName())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else{
            ImmutablePair<Integer, Device> servRes = linkUserToDeviceService.addThreshold(user.get(),deviceMacAddress,energyThreshold);
            if(servRes.getKey().equals(200)) {
                return new ResponseEntity<>( "Threshold has been set to : " + servRes.getValue().getEnerygyThreshold() ,HttpStatus.OK);
            } else if (servRes.getKey().equals(403)) {
                return new ResponseEntity("Device not linked", HttpStatus.FORBIDDEN);
            } else if (servRes.getKey().equals(404)) {
                return new ResponseEntity("Device not found", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity("Something went wrong...", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    }

}
