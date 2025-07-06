package io.erisdev.certificatemanagerbackend.config;

import io.erisdev.certificatemanagerbackend.model.Role;
import io.erisdev.certificatemanagerbackend.model.User;
import io.erisdev.certificatemanagerbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());
        System.out.println("User: " + user.getUsername());
        System.out.println("Hash: " + user.getPassword());
        System.out.println("Roles: " + user.getRoles());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("Matches admin: " + encoder.matches("admin", "$2a$10$P8D5NQRGVONlCkHMCO2MCO7HjfQEeU6RBoy.sLH5k7duZUIDkBmlu"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}