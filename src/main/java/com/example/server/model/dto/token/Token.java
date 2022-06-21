package com.example.server.model.dto.token;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Token {
    private Long id;
    private Long employeeUsername;
    private String refreshToken;

    @Builder
    public Token(Long employeeUsername, String refreshToken) {
        this.employeeUsername = employeeUsername;
        this.refreshToken = refreshToken;
    }



}
