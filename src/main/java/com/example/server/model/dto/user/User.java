package com.example.server.model.dto.user;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor

public class User {
    private Long id;
    private String username;
    private String password;
    private String roles;
    private String n_password;

    @Builder
    public User(Long id, String username, String password, String roles, String n_password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.n_password = n_password;
    }

    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public User toEntity(BCryptPasswordEncoder bCryptPasswordEncoder){
        return User.builder().username(username).password(bCryptPasswordEncoder.encode(password)).roles(roles).build();
    }

    public User pwBcrypt(BCryptPasswordEncoder bCryptPasswordEncoder){
        return User.builder().password(bCryptPasswordEncoder.encode(password)).build();
    }

}
