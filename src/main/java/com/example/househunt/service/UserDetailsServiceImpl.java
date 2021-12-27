package com.example.househunt.service;

import static java.util.Collections.singletonList;

import com.example.househunt.hunter.domain.Hunter;
import com.example.househunt.hunter.repository.HunterRepository;
import java.util.Collection;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final HunterRepository hunterRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Optional<Hunter> userOptional = hunterRepository.findByUserName(username);
        Hunter hunter = userOptional.orElseThrow(() -> new UsernameNotFoundException("No user " + "Found with username : " + username));

        return new org.springframework.security.core.userdetails.User(
            hunter.getUserName(),
            hunter.getPassword(),
            hunter.getActive(),
            true,
            true,
            true,
            getAuthorities("USER")
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }
}
