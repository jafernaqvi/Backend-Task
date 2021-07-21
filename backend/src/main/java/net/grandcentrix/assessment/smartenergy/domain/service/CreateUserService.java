package net.grandcentrix.assessment.smartenergy.domain.service;

import net.grandcentrix.assessment.smartenergy.domain.model.AppUser;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * The service responsible to create a new user. The service will encode the password of the user with the provided {@link PasswordEncoder}.
 */
@Service
public class CreateUserService {

    private final AppUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public CreateUserService(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Stores a new user in the user repository. The users' password is encoded with the provided {@link PasswordEncoder}.
     *
     * @param name     The name of the user
     * @param password The raw password of the user
     * @return The {@link AppUser} that is stored into the repository.
     */
    public ImmutablePair<Integer,AppUser> createUser(String name, String password) {
        if(password.equals("") || password == null){
            return new ImmutablePair<>(400,null);
        }
        if(userRepository.findByUsername(name).isPresent()){
            return new ImmutablePair<>(409,null);
        }

        return new ImmutablePair<>(200,userRepository.save(new AppUser(name, passwordEncoder.encode(password))));
    }
}
