package com.lm.hospital.security;

import com.lm.hospital.model.LMUser;
import com.lm.hospital.repository.LMUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LMUserDetailsService implements UserDetailsService {

    @Autowired
    private LMUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LMUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        
        // FIX: Use isActive() method instead of getActive()
        if (!user.isActive()) {
            throw new UsernameNotFoundException("User is disabled: " + username);
        }
        
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
