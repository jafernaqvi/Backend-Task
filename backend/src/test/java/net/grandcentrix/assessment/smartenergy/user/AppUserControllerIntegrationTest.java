package net.grandcentrix.assessment.smartenergy.user;

import net.grandcentrix.assessment.smartenergy.api.WebPaths;
import net.grandcentrix.assessment.smartenergy.api.device.DeviceRegisterRequest;
import net.grandcentrix.assessment.smartenergy.api.device.DeviceRegisterResponse;
import net.grandcentrix.assessment.smartenergy.api.user.CreateUserRequest;
import net.grandcentrix.assessment.smartenergy.api.user.GetUserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;


import java.net.URI;
import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppUserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getUserControllerPath() {
        return "http://localhost:" + port + "/" + WebPaths.USERS;
    }

    @Test
    public void userResourceCanBeGetAfterCreation() throws NoSuchFieldException {
        String username = "newUser";
        String password = "abc123";
        URI location = restTemplate.postForLocation(getUserControllerPath(), new CreateUserRequest(username, password));
        LinkedHashMap hashMap = (LinkedHashMap) restTemplate
                .withBasicAuth(username, password)
                .getForEntity(location, Object.class)
                .getBody();
        assertThat(hashMap.get("name")).isEqualTo(username);
    }

    @Test
    public void getUserIsNotAccessibleWithoutAuthentication() {
        URI location = restTemplate.postForLocation(getUserControllerPath(), new CreateUserRequest("notAuthorized", "superS3cret"));

        HttpStatus statusCode = restTemplate
                .getForEntity(location, Object.class)
                .getStatusCode();


        assertThat(statusCode).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void getUserCantBeCalledFromOtherUser() {
        //first create a user that we try to access
        URI location = restTemplate.postForLocation(getUserControllerPath(), new CreateUserRequest("firstUser", "IAmGroot"));

        String username = "otherUser";
        String password = "ILikeTrees";
        restTemplate.postForLocation(getUserControllerPath(), new CreateUserRequest(username, password));

        HttpStatus statusCode = restTemplate
                .withBasicAuth(username, password) //but we use the second user to access the data of the first
                .getForEntity(location, Object.class)
                .getStatusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void cantCreateAUserWithTheSameUsername() {
        String username = "sameUsername";
        restTemplate.postForEntity(getUserControllerPath(), new CreateUserRequest(username, "password"), Void.class);
        HttpStatus statusCode = restTemplate
                .postForEntity(getUserControllerPath(), new CreateUserRequest(username, "MayThe4th"), Void.class)
                .getStatusCode();
        assertThat(statusCode).isNotEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void unableToAccessGetUserWithDeviceCredentials() {

        URI userLocation = restTemplate.postForLocation(getUserControllerPath(), new CreateUserRequest("DarthVader", "Luke"));

        String deviceMacAddress = "foo";
        DeviceRegisterRequest registerDeviceRequest = new DeviceRegisterRequest(deviceMacAddress);
        DeviceRegisterResponse response = restTemplate.postForEntity("http://localhost:" + port + "/" + WebPaths.DEVICES, registerDeviceRequest, DeviceRegisterResponse.class).getBody();
        String devicePassword = response.getPassword();

        HttpStatus statusCode = restTemplate
                .withBasicAuth(deviceMacAddress, devicePassword)
                .getForEntity(userLocation, GetUserResponse.class)
                .getStatusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.UNAUTHORIZED);

    }


}
