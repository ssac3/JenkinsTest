package com.example.server.service;

import com.example.server.constants.JsonResponse;
import com.example.server.constants.StatusCode;
import com.example.server.model.dao.token.TokenMapper;
import com.example.server.model.dao.user.UserMapper;
import com.example.server.model.dto.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;


@Service
@RequiredArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;
    private final TokenMapper tokenMapper;
    private StatusCode statusCode;

    public void deleteById(Long username){
        tokenMapper.deleteById(username);
    }

    public String selectPw(String username) {
        return userMapper.findByUsername(Long.parseLong(username)).getPassword();
    }

    public ResponseEntity<StatusCode> updatepw(String username, User user){
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
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

    public ResponseEntity<StatusCode> myView(String username){
        System.out.println(username);
        statusCode = StatusCode.builder().resCode(0).resMsg("회원 정보 조회 성공")
                .data(userMapper.myView(Long.parseLong(username))).build();
        System.out.println(statusCode.getData().toString());
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

    public ResponseEntity<StatusCode> logout(String username) {
            statusCode = StatusCode.builder().resCode(0).resMsg("로그아웃 성공").build();
            deleteById(Long.parseLong(username));
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

    public void saveUser(User user){
        userMapper.save(user.toEntity(bCryptPasswordEncoder));
    }
}
