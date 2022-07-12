package com.example.server.model.dao.admin;

import com.amazonaws.services.dynamodbv2.xspec.L;
import com.example.server.model.dto.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
// User랑 Admin DTO랑 유사 -> User 사용
public interface AdminMapper {
    // 사원번호생성

    //사원등록
    void insertEmp(User user);


    // 사원조회
    List<User> viewEmp(User user);

    //사원수정
    int updateEmp(User user);

    // 사원삭제
//    int deleteEmp(Map<String,List<String>> user);
    int deleteEmp(Long username);


    ArrayList<String> selectUsername();
}
