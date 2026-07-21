package com.company.taskportal.security;

import com.company.taskportal.entity.User;
import com.company.taskportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsernameAndDeletedFalse(username.trim())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + username));

        if (!Boolean.TRUE.equals(user.getActive())) {
            throw new UsernameNotFoundException("User account is inactive.");
        }

        return user;
    }
}