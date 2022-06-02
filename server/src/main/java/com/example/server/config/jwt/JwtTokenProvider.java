package com.example.server.config.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component // 개발자가 직접 작성한 class를 Bean으로 등록하기 위한 어노테이션
@NoArgsConstructor
public class JwtTokenProvider {

    public String creatAccessToken(String username){ // AccessToken 생성함수
        return JWT.create()
            .withSubject("jwt_token")
            .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.ACCESS_EXPIRATION_TIME))
            .withClaim("username", username)
            .sign(Algorithm.HMAC256(JwtProperties.SECRET));
    }
    public String createRefreshToken(){ // Refresh Token 생성 함수
        return JWT.create()
            .withExpiresAt(new Date(System.currentTimeMillis()  +JwtProperties.REFRESH_EXPIRATION_TIME) )
            .sign(Algorithm.HMAC256(JwtProperties.SECRET));
    }

    public String resolveJwtToken(HttpServletRequest request){ // Access Token 값 추출
        return request.getHeader(JwtProperties.HEADER_STRING);
    }

    public DecodedJWT getVerifyToken(String token){ // 토큰 검증
        return JWT.require(Algorithm.HMAC256(JwtProperties.SECRET)).build().verify(token);
    }

    public boolean accessTokenValid(String accessToken){ // AccessToken 유효성 확인
        try{
            return !getVerifyToken(accessToken).getExpiresAt().before(new Date());
        }catch (Exception e){
            return false;
        }
    }

    public boolean refreshTokenValid(String refreshToken){ // Refresh Token 유효성 확인
        try{
            return !getVerifyToken(refreshToken).getExpiresAt().before(new Date());
        }catch (Exception e){
            return false;
        }
    }
}
