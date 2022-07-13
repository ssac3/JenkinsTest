package com.example.server.config.jwt;

import com.example.server.config.auth.PrincipalDetails;
import com.example.server.constants.StatusCode;
import com.example.server.model.dao.token.TokenMapper;
import com.example.server.model.dto.token.Token;
import com.example.server.model.dto.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final TokenMapper tokenRepository;
    private  final JwtTokenProvider jwtTokenProvider;

    private ObjectMapper om = new ObjectMapper();

    @Override
    public  Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {


        try {
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            return authentication;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        
        String jwtToken = jwtTokenProvider.creatAccessToken(principalDetails.getUsername()); // accessToken 발급
        String refreshToken = jwtTokenProvider.createRefreshToken(); // refreshToken 발급
        
        Token token = Token.builder().username(Long.parseLong(principalDetails.getUsername())).refreshToken(refreshToken).build(); // refreshToken DB에 저장
        tokenRepository.save(token);
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
        response.addHeader(JwtProperties.REFRESH_HEADER_STRING, refreshToken);
        String getRole = principalDetails.getUser().getRole();
        String getName = principalDetails.getUser().getName();
        StatusCode statusCode;
        Long depId = principalDetails.getUser().getDepId();
        if(getRole.equals("0")) {
            System.out.println("[ADMIN]");
            statusCode =  StatusCode.builder().resCode(0).data(User.builder().role(getRole).name(getName).build()).resMsg("로그인 성공").build();
        } else if(getRole.equals("1")) {
            System.out.println("[MANAGER]");
            statusCode = StatusCode.builder().resCode(0).data(User.builder().depId(depId).role(getRole).name(getName).build()).resMsg("로그인 성공").build();
        } else {
            System.out.println("[USER]");
            statusCode = StatusCode.builder().resCode(0).data(User.builder().role(getRole).name(getName).build()).resMsg("로그인 성공").build();
        }
        ObjectMapper om = new ObjectMapper();
        String result = om.writeValueAsString(statusCode);
        response.getWriter().write(result);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        StatusCode statusCode = StatusCode.builder().resCode(1).resMsg( "유효하지 않는 사용자입니다.").build();
        ObjectMapper om = new ObjectMapper();
        String result = om.writeValueAsString(statusCode);
        response.getWriter().write(result);
    }

}
