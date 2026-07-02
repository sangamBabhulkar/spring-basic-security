package com.example.demo.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // Mocking Database Users
        if ("admin".equals(username)) {
            return new User("admin", encoder.encode("admin123"), 
                    List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        } 
        if ("user".equals(username)) {
            return new User("user", encoder.encode("user123"), 
                    List.of(new SimpleGrantedAuthority("ROLE_USER")));
        }
        
        throw new UsernameNotFoundException("User not found: " + username);
    }
}