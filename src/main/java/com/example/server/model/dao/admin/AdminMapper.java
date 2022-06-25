package com.example.server.model.dao.admin;

import com.example.server.model.dto.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
// User랑 Admin DTO랑 유사 -> User 사용
public interface AdminMapper {

    //사원등록
    void insertEmp(User user);

    // 사원조회
    User findByUsername(Long username);

    //사원수정
    void updateEmp(User user);


}
