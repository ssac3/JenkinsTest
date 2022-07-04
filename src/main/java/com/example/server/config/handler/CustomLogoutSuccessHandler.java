package com.example.server.config.handler;

import com.example.server.config.jwt.JwtProperties;
import com.example.server.config.jwt.JwtTokenProvider;
import com.example.server.constants.StatusCode;
import com.example.server.model.dao.token.TokenMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RequiredArgsConstructor
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    
    private final TokenMapper tokenMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private ObjectMapper om = new ObjectMapper();
    private StatusCode statusCode = new StatusCode();
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("request.getHeader(\"Authorization\") = " + request.getHeader("Authorization"));
        String jwtHeader = jwtTokenProvider.resolveJwtToken(request);
        String accessToken = jwtHeader.replace(JwtProperties.TOKEN_PREFIX, "");
        String username = jwtTokenProvider.getVerifyToken(accessToken).getClaim("username").asString();
        System.out.println("username = " + username);
        if(tokenMapper.deleteById(Long.parseLong(username)) > 0){
            statusCode.setResCode(0); statusCode.setResMsg("로그아웃 성공");
            String result = om.writeValueAsString(statusCode);
            response.getWriter().write(result);
        }
        else {
            statusCode.setResCode(1); statusCode.setResMsg("로그아웃 실패");
            String result = om.writeValueAsString(statusCode);
            response.getWriter().write(result);
        }
    }
}