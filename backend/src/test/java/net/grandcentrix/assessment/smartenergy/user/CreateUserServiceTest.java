package net.grandcentrix.assessment.smartenergy.user;

import net.grandcentrix.assessment.smartenergy.domain.model.AppUser;
import net.grandcentrix.assessment.smartenergy.domain.service.AppUserRepository;
import net.grandcentrix.assessment.smartenergy.domain.service.CreateUserService;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AppUserRepository userRepository;

    private CreateUserService createUserService;

    @BeforeEach
    public void setUp() {
        createUserService = new CreateUserService(userRepository, passwordEncoder);
    }

    @Test
    public void createUser_userPasswordIsEncoded() {
        String rawPassword = "bar";
        String encodedPassword = "encodedBar";
        String username = "foo";

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        createUserService.createUser(username, rawPassword);

        verify(userRepository).save(refEq(new AppUser(username, encodedPassword), "id"));
    }

    @Test
    public void createUser_emptyPassword_throwsException() {
        String username = "foo";
        String rawPassword = "";

        ImmutablePair<Integer, AppUser> res = createUserService.createUser(username, rawPassword);
        ImmutablePair<Integer, AppUser> expectedRes =  new ImmutablePair<>(400,null);
        // this should do the trick
        Assertions.assertEquals(expectedRes, res);
//        Assertions.fail("It should not be allowed to create a user with an empty password. " +
//                "Implement the necessary test with the according implementation.");
    }

}
