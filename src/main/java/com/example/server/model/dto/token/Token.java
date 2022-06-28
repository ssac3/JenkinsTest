package com.example.server.model.dto.token;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Token {
    private Long id;
    private Long username;
    private String refreshToken;

    @Builder
    public Token(Long username, String refreshToken) {
        this.username = username;
        this.refreshToken = refreshToken;
    }



}
