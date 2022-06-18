package com.example.server.model.dto;

import com.example.server.model.dto.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {
    private String password;

    @Builder
    public UserSaveRequestDto(String password) {
        this.password = password;
    }

    public User toEntity(BCryptPasswordEncoder bCryptPasswordEncoder){
        return User.builder().password(bCryptPasswordEncoder.encode(password)).build();
    }
}
