package com.example.server.config;


import com.example.server.config.handler.CustomLogoutSuccessHandler;
import com.example.server.config.jwt.JwtAuthenticationFilter;
import com.example.server.config.jwt.JwtAuthorizationFilter;
import com.example.server.config.jwt.JwtTokenProvider;
import com.example.server.model.dao.token.TokenMapper;
import com.example.server.model.dao.user.UserMapper;
import com.example.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsConfig;
    private final UserMapper userRepository;
    private final TokenMapper tokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilter(corsConfig)
            .formLogin().disable()
            .httpBasic().disable()
            .logout()
            .logoutUrl("/logout")
            .addLogoutHandler(customLogoutSuccessHandler)
            .deleteCookies("JSESSIONID")
            .and()
            .addFilter(new JwtAuthenticationFilter(authenticationManager(), tokenRepository, jwtTokenProvider))
            .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository, tokenRepository, jwtTokenProvider))
            .authorizeRequests()
            .antMatchers("/user/**").access("hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
            .antMatchers("/manager/**").access("hasRole('ROLE_MANAGER')")
            .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
            .anyRequest().permitAll();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
