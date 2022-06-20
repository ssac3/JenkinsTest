package com.example.server.model.dao.user;

import com.example.server.model.dto.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    User findByUsername(Long username);
    void updateByUsername(User user);
    void save(User user);

    User pwBcrypt(String n_password);
}
