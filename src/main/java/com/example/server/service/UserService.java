package com.example.server.service;


import com.example.server.domain.userRepository.UserRepository;
import com.example.server.model.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    @Transactional
    public void saveUser(UserSaveRequestDto userSaveRequestDto){
        userRepository.save(userSaveRequestDto.toEntity(bCryptPasswordEncoder));
    }
}
