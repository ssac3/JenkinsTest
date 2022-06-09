package com.example.server.web.dto;

import com.example.server.domain.userRepository.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {

    private String username;
    private String password;
    private String roles;

    @Builder
    public UserSaveRequestDto(String username, String password, String roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User toEntity(BCryptPasswordEncoder bCryptPasswordEncoder){
        return User.builder().username(username).password(bCryptPasswordEncoder.encode(password)).roles(roles).build();
    }
}
