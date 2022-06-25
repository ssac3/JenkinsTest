package com.example.server.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.server.config.jwt.JwtProperties;
import com.example.server.constants.JsonResponse;
import com.example.server.constants.StatusCode;
import com.example.server.model.dao.token.TokenMapper;
import com.example.server.model.dao.user.UserMapper;
import com.example.server.model.dto.user.MonthJoin;
import com.example.server.model.dto.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;


@Service
@RequiredArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;
    private final TokenMapper tokenMapper;
    private StatusCode statusCode;

    private User user;


    public void deleteById(Long username){
        tokenMapper.deleteById(username);
    }

    public String selectPw(String username) {
        return userMapper.findByUsername(Long.parseLong(username)).getPassword();
    }

    public ResponseEntity<StatusCode> updatepw(HttpServletRequest request, User user){
        String username = request.getAttribute("username").toString();
        if(username != null && !username.equals("")){
//            String pw = bCryptPasswordEncoder.encode(user.getPassword());
            String pw_check = selectPw(username);
            if (bCryptPasswordEncoder.matches(user.getPassword(), pw_check)){
                String n_pw = bCryptPasswordEncoder.encode(user.getNPassword());
                System.out.println(n_pw);
                userMapper.updateByUsername(User.builder().username(Long.parseLong(username))
                        .password(n_pw).build());
                statusCode = StatusCode.builder().resCode(0).resMsg("비밀번호 수정 성공").build();
            }
            else{
                statusCode = StatusCode.builder().resCode(2).resMsg("현재 비밀번호가 일치하지 않습니다..").build();
            }
        }
        else{
            statusCode = StatusCode.builder().resCode(1).resMsg("에러 발생").build();
        }
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

    public ResponseEntity<StatusCode> myView(String username){
        System.out.println(username);
//        User user = userMapper.myView(Long.parseLong(username));
//        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
//        map.put("name", user.getName());
//        map.put("department", user.getDepartment());
//        map.put("position", user.getPosition());
//        map.put("email", user.getEmail());
//        map.put("manager", user.getManager());
//        map.put("location", user.getLocation());
//        map.put("qrPath", user.getQrPath());
//        Object data = map;
//        System.out.println("data = " + data);
        statusCode = StatusCode.builder().resCode(0).resMsg("회원 정보 조회 성공")
                .data(userMapper.myView(Long.parseLong(username))).build();
        System.out.println(statusCode.getData().toString());
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

    public ResponseEntity<StatusCode> logout(HttpServletRequest request) {
        String username = request.getAttribute("username").toString();
        System.out.println(username);
        if(username != null && !username.equals("")){
            statusCode = StatusCode.builder().resCode(0).resMsg("로그아웃 성공").build();
            deleteById(Long.parseLong(username));
        }
        else{
            statusCode = StatusCode.builder().resCode(2).resMsg("에러 발생").build();
        }
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

    public void saveUser(User user){
        userMapper.save(user.toEntity(bCryptPasswordEncoder));
    }
}
