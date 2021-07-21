package net.grandcentrix.assessment.smartenergy.api.user.security;

import net.grandcentrix.assessment.smartenergy.domain.model.AppUser;
import net.grandcentrix.assessment.smartenergy.domain.service.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * The {@link AppUser} stores the authentication that is used for the basic authentication. This service provides the
 * functionality to load a user from the database
 *
 * @see AppUserSecurityConfig
 * @see AppUser
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

    private final AppUserRepository userRepository;

    public AppUserDetailsService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow();
    }
}
