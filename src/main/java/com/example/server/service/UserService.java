package com.example.server.service;


import com.example.server.domain.userRepository.UserRepository;
import com.example.server.model.dao.user.UserMapper;
import com.example.server.model.dto.UserSaveRequestDto;
import com.example.server.model.dto.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public void saveUser(UserSaveRequestDto userSaveRequestDto){
        userRepository.save(userSaveRequestDto.toEntity(bCryptPasswordEncoder));
    }
    public User selectUser(String username){
        return userMapper.findByUsername(username);
    }

}
