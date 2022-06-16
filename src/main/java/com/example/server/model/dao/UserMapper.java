package com.example.server.model.dao;

import com.example.server.model.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper

public interface UserMapper {
    public User findByUsername(String username);
}
