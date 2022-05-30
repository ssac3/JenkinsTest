package com.example.server.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.server.config.auth.PrincipalDetails;
import com.example.server.domain.userRepository.User;
import com.example.server.domain.userRepository.UserRepository;
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

public class JwtAuthoriazationFilter extends BasicAuthenticationFilter {
    private UserRepository userRepository;

    public JwtAuthoriazationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwtHeader = request.getHeader(JwtProperties.JWT_HEADER);

        if(jwtHeader == null || !jwtHeader.startsWith(JwtProperties.JWT_PREFIX)){
            chain.doFilter(request,response);
            return;
        }

        String jwtToken = jwtHeader.replace(JwtProperties.JWT_PREFIX, "");

        String username = JWT.require(Algorithm.HMAC256(JwtProperties.SECRET_KEY)).build().verify(jwtToken).getClaim("username").asString();

        if(username != null && !username.equals("")){
            User user = userRepository.findByUsername(username);
            PrincipalDetails principalDetails = new PrincipalDetails(user);

            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails.getUsername(), null, principalDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        }
    }
}
