package com.example.server.model.dto.token;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class Token {
    private Long id;
    private Long EMPLOYEE_username;
    private String refresh_token;

    @Builder
    public Token(Long EMPLOYEE_username, String refresh_token) {
        this.EMPLOYEE_username = EMPLOYEE_username;
        this.refresh_token = refresh_token;
    }



}
