package com.example.server.model.dao.token;

import com.example.server.model.dto.token.Token;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TokenMapper {
    Token findByUsername(Long username);
    void deleteById(Long username);
    void save(Token token);

}
