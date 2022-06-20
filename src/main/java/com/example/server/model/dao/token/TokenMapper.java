package com.example.server.model.dao.token;

import com.example.server.model.dto.token.Token;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper

public interface TokenMapper {
    Token findByUsername(String username);
    void deleteById(String username);
    void save(Token token);
}
