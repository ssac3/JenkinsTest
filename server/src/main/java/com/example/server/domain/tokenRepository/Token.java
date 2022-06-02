package com.example.server.domain.tokenRepository;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Getter
@Entity
@NoArgsConstructor
@Table
public class Token {
    @Id
    @Column(name="username")
    private String username;

    @Column(nullable = false)
    private String token;

    @Builder
    public Token(String username, String token) {
        this.username = username;
        this.token = token;
    }
}
