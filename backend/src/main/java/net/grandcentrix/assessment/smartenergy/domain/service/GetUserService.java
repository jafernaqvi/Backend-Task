package net.grandcentrix.assessment.smartenergy.domain.service;

import net.grandcentrix.assessment.smartenergy.domain.model.AppUser;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetUserService {

    private final AppUserRepository userRepository;

    public GetUserService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<AppUser> getUser(UUID userId) {
        return userRepository.findById(userId);
    }
}
