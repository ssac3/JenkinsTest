package com.example.server.service;

import com.example.server.constants.JsonResponse;
import com.example.server.constants.StatusCode;
import com.example.server.model.dao.admin.AdminMapper;
import com.example.server.model.dto.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor // 하는 이유 질문
public class AdminService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AdminMapper adminMapper;
    private StatusCode statusCode;

    //사원등록
    @Transactional
    public ResponseEntity<StatusCode> insertEmp(String userInfo, User user){
       //username 유효성겁사(중복)

        System.out.println("userInfo = " + userInfo);
        System.out.println("user정보"+user);
        //System.out.println("password" + user.getPassword());
        if(userInfo != null && !userInfo.equals("")){
            // 사원 등록
            System.out.println("사원등록");
            adminMapper.insertEmp(user.toInsertEntity(bCryptPasswordEncoder));
            System.out.println("사원등록중간");
            statusCode = StatusCode.builder().resCode(0).resMsg("사원등록을 성공했습니다").build();
            System.out.println("사원등록성공");
        }else {
            System.out.println("[ERR] 유효하지 않는 사용자 정보입니다.");
            statusCode = StatusCode.builder().resCode(2).resMsg("유효하지 않는 사용자 정보입니다.").build();
        }
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

    // 사원리스트정보
    @Transactional
    public ResponseEntity<StatusCode> viewEmp(String userInfo, User user) {

        if(userInfo != null && !userInfo.equals("")){
            // 사원의 리스트 보여주기, User의 정보를 전부 다 받아와야한다.
            System.out.println("회원리스트보여주기");
            List<User> empList = adminMapper.viewEmp(user);
            // 데이터 확인하기
            for (User item: empList
                 ) {
                System.out.println(item.getCreatedAt());
            }
            statusCode = StatusCode.builder().resCode(0).resMsg("사원조회를 성공했습니다").build();
        }else {
            System.out.println("[ERR] 유효하지 않는 사용자 정보입니다.");
            statusCode = StatusCode.builder().resCode(2).resMsg("유효하지 않는 사용자 정보입니다.").build();
        }
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

    //사원디테일리스트
    @Transactional
    public ResponseEntity<StatusCode> viewEmpDetail(String userInfo, User user) {

        if(userInfo != null && !userInfo.equals("")){
            System.out.println("회원디테일리스트보여주기");
            adminMapper.viewEmpDetail(user);
            System.out.println(user);

            statusCode = StatusCode.builder().resCode(0).resMsg("사원디테일조회를 성공했습니다").build();
        }else {
            System.out.println("[ERR] 유효하지 않는 사용자 정보입니다.");
            statusCode = StatusCode.builder().resCode(2).resMsg("유효하지 않는 사용자 정보입니다.").build();
        }
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }
    
    //사원삭제
    @Transactional
    public ResponseEntity<StatusCode> deleteEmp(String userInfo, User user) {
        if(userInfo != null && !userInfo.equals("")){
            System.out.println("사원삭제");
            adminMapper.deleteEmp(user);
            statusCode = StatusCode.builder().resCode(0).resMsg("사원삭제를 성공했습니다").build();
        }else {
            System.out.println("[ERR] 유효하지 않는 사용자 정보입니다.");
            statusCode = StatusCode.builder().resCode(2).resMsg("유효하지 않는 사용자 정보입니다.").build();
        }
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

    // 사원수정
    public ResponseEntity<StatusCode> updateEmp(String userInfo, User user) {
        if(userInfo != null && !userInfo.equals("")){
            System.out.println("사원수정");
            adminMapper.updateEmp(user);
            statusCode = StatusCode.builder().resCode(0).resMsg("사원수정을 성공했습니다").build();
        }else {
            System.out.println("[ERR] 유효하지 않는 사용자 정보입니다.");
            statusCode = StatusCode.builder().resCode(2).resMsg("유효하지 않는 사용자 정보입니다.").build();
        }
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }
}

