package uz.pdp.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import uz.pdp.entity.AuthUser;
import uz.pdp.repository.AuthUserRepository;

import java.util.*;

@Component
public class CustomUserDetailsService implements UserDetailsService {


    private final AuthUserRepository authUserRepository;

    public CustomUserDetailsService(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<AuthUser> optional = authUserRepository.findAuthUserByUsername(username);
        AuthUser authUser = optional.orElseThrow(() -> new UsernameNotFoundException(username));

        List<GrantedAuthority> authorities = Collections.singletonList( new SimpleGrantedAuthority( authUser.getRole().name() ) );

        return new User(authUser.getUsername(), authUser.getPassword(), authorities );
    }



}
