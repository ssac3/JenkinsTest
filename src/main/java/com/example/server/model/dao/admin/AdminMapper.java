package com.example.server.model.dao.admin;

import com.example.server.model.dto.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Repository
@Mapper
// User랑 Admin DTO랑 유사 -> User 사용
public interface AdminMapper {
    // 사원번호생성

    //사원등록
    void insertEmp(User user);

    // 사원번호 생성
    void mkUsername();

    // 사원조회
    List<User> viewEmp(User user);

    //사원수정
    int updateEmp(User user);

    //사원삭제
    void deleteEmp(User user);



    ArrayList<String> selectUsername();
}
