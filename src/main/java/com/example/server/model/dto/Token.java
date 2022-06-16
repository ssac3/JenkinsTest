package com.example.server.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Token {
    private String username;
    private String token;
}
