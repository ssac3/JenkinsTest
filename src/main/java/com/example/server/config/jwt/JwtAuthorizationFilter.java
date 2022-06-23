package com.example.server.config.jwt;

import com.auth0.jwt.JWT;
import com.example.server.config.auth.PrincipalDetails;
import com.example.server.constants.StatusCode;
import com.example.server.model.dao.token.TokenMapper;
import com.example.server.model.dao.user.UserMapper;
import com.example.server.model.dto.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UserMapper userRepository;
    private TokenMapper tokenRepository;

    private JwtTokenProvider jwtTokenProvider;
    private ObjectMapper om = new ObjectMapper();
    private StatusCode statusCode = new StatusCode();
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserMapper userRepository, TokenMapper tokenRepository, JwtTokenProvider jwtTokenProvider) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwtHeader = jwtTokenProvider.resolveJwtToken(request);

        if (jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String accessToken = jwtHeader.replace(JwtProperties.TOKEN_PREFIX, "");

        if (jwtTokenProvider.accessTokenValid(accessToken)) { // AccessToken 유효성(만료시간) 검사
            String username = jwtTokenProvider.getVerifyToken(accessToken).getClaim("username").asString();

            if (username != null && !username.equals("")) {
                User user = userRepository.findByUsername(Long.parseLong(username));
                PrincipalDetails principalDetails = new PrincipalDetails(user);

                Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                request.setAttribute("username",username);
                chain.doFilter(request, response);
            }else{
                System.out.println("[ERR] ACCESS TOKEN 사용자 정보 에러");
                statusCode.setResCode(1); statusCode.setResMsg("[ERR] ACCESS TOKEN 사용자 정보 에러");
                String result = om.writeValueAsString(statusCode);
                response.getWriter().write(result);
                // : /login 으로 리다이렉트 ?
                return;
            }
        }
        else{
            // Access Token 만료된 경우
            System.out.println("[WARN] Expired Access Token");

            // 클라이언트가 Refresh Token을 들고 요청한 경우
            if(request.getHeader(JwtProperties.REFRESH_HEADER_STRING) != null){

                // Refresh 유효성(만료시간) 검사
                String refresh = request.getHeader(JwtProperties.REFRESH_HEADER_STRING);
                System.out.println("refresh = " + refresh);
                String username = JWT.decode(accessToken).getClaim("username").asString();
                // DB의 Refresh와 클라이언트에서 받은 Refresh 비교
                if(refresh.equals(tokenRepository.findByUsername(Long.parseLong(username)).getRefreshToken())){
                    System.out.println("[SUCCESS] 정상적인 Refresh Token");

                    if(jwtTokenProvider.refreshTokenValid(refresh)){ // refresh token 만료 여부 확인
                        String reissueAccessToken = jwtTokenProvider.creatAccessToken(username);
                        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + reissueAccessToken);
                    }else{
                        System.out.println("[WARN] Refresh Token 만료됨, 재로그인 요청");
                        tokenRepository.deleteById(Long.parseLong(username));
                        statusCode.setResCode(2); statusCode.setResMsg("만료된 Refresh Token");
                        String result = om.writeValueAsString(statusCode);
                        response.getWriter().write(result);
                        return;
                    }
                }else{
                    System.out.println("[ERR] 비정상적인 Refresh Token");
                    tokenRepository.deleteById(Long.parseLong(username)); // DB에 존재하는 refresh token 삭제
                    statusCode.setResCode(2); statusCode.setResMsg("비정상적인 Refresh Token");
                    String result = om.writeValueAsString(statusCode);
                    response.getWriter().write(result);
                    return;
                }

            }else{
                statusCode.setResCode(1); statusCode.setResMsg("Access Token 만료됨");
                String result = om.writeValueAsString(statusCode);
                response.getWriter().write(result);
                return;
            }

        }


    }
}
