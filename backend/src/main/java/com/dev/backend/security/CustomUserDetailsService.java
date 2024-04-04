package com.dev.backend.security;

import com.dev.backend.document.UserDocument;
import com.dev.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(user -> new User(
                        mapUserToUserDetails(user).getUsername(),
                        mapUserToUserDetails(user).getPassword(),
                        mapUserToUserDetails(user).getAuthorities()
                ))
                .orElseThrow(()->new UsernameNotFoundException("username not found"));
    }
    public UserDetails mapUserToUserDetails(UserDocument user){
        return new CustomUserDetails(user);
    }
}
