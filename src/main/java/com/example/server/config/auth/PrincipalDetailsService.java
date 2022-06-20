package com.example.server.config.auth;

import com.example.server.model.dao.user.UserMapper;
import com.example.server.model.dto.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserMapper userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(Long.parseLong(username));
        System.out.println(userEntity.getUsername());
        return new PrincipalDetails(userEntity);
    }
}
