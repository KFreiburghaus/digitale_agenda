package com.example.demo.service;

import com.example.demo.model.Owner;
import com.example.demo.repository.OwnerRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final OwnerRepository ownerRepository;

    public UserDetailsServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Owner> userOptional = ownerRepository.findByEmail(s);
        Owner owner = userOptional
                .orElseThrow(() -> new UsernameNotFoundException("No owner " +
                        "Found with username : " + s));

        return new org.springframework.security
                .core.userdetails.User(owner.getEmail(), owner.getPassword(),
                owner.isEnabled(), true, true,
                true, getAuthorities("OWNER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }

}
