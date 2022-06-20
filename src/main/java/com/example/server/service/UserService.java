package com.example.server.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.server.config.jwt.JwtProperties;
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


@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserMapper userMapper;

    private final TokenMapper tokenMapper;

    public User selectUser(String username) {
        return userMapper.findByUsername(username);
    }

    public void deleteById(String username){
        tokenMapper.deleteById(username);
    }

    public String selectPw(String username) {
        return userMapper.findByUsername(username).getPassword();
    }

    //    userMapper.updateByUsername(User.pwBcrypt(bCryptPasswordEncoder));
    public boolean comparepw(String username, String password) {
        String pw = selectPw(username);
        String n_pw = userMapper.pwBcrypt(password).getPassword();
        if (pw.equals(n_pw)) {
            return true;
        }
        return false;
    }
    public String tokendecoder(String token){
        String jwtToken = token.replace("Bearer ", ""); // Bearer를 제외한 실제 토큰 값만 추출
        // JWT 검증 => 검증 실패 시 exception 발생, 통과는 서명이 완료되었다는 것을 의미함
        String username = JWT.require(Algorithm.HMAC256(JwtProperties.SECRET)).build().verify(jwtToken).getClaim("username").asString();
        return username;
    }

    public ResponseEntity<StatusCode> updatepw(User user, String token){
        StatusCode statusCode;
        String username = tokendecoder(token);
        if(comparepw(username, user.getPassword())){
            String changePw = userMapper.pwBcrypt(user.getN_password()).getPassword();
            userMapper.updateByUsername(User.builder().username(username).password(changePw).build());
        }
        else{
            statusCode = StatusCode.builder().resCode(2).resMsg("현재 비밀번호가 일치하지 않습니다..").build();
        }
        statusCode = StatusCode.builder().resCode(0).resMsg("비밀번호 수정 성공").build();
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

    public ResponseEntity<StatusCode> myview(String token){
        String username = tokendecoder(token);
        System.out.println(username);
        StatusCode statusCode;
        if(username != null && !username.equals("")){
            statusCode = StatusCode.builder().resCode(0).resMsg("회원 정보 조회 성공").data(selectUser(username)).build();

        }
        else{
            statusCode = StatusCode.builder().resCode(2).resMsg("에러 발생").build();
        }
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

    public ResponseEntity<StatusCode> logout(String token){
        String username = tokendecoder(token);
        System.out.println(username);
        StatusCode statusCode;
        if(username != null && !username.equals("")){
            statusCode = StatusCode.builder().resCode(0).resMsg("로그아웃 성공").build();
            deleteById(username);
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
