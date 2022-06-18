package com.example.server.domain.tokenRepository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, String> {
    Token findByUsername(String username);
}
