package com.example.server.model.dto.token;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor

public class Token {
    private String username;
    private String token;

    @Builder
    public Token(String username, String token) {
        this.username = username;
        this.token = token;
    }

}
