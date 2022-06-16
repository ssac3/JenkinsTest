package com.example.server.model.dao;

import com.example.server.model.dto.Token;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper

public interface TokenMapper {
    public Token findByUsername(String username);
}
