package com.example.server.config;


import com.example.server.config.jwt.JwtAuthenticationFilter;
import com.example.server.config.jwt.JwtAuthorizationFilter;
import com.example.server.config.jwt.JwtTokenProvider;
import com.example.server.model.dao.token.TokenMapper;
import com.example.server.model.dao.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsConfig;
    private final UserMapper userRepository;

    private final TokenMapper tokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilter(corsConfig)
            .formLogin().disable()
            .httpBasic().disable()
            //.addFilter(new JwtAuthenticationFilter(authenticationManager(), tokenRepository, jwtTokenProvider))
            //.addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository, tokenRepository, jwtTokenProvider))
            .authorizeRequests()
           // .antMatchers("/user/**").access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER')")
            //.antMatchers("/manager/**").access("hasRole('ROLE_MANAGER')")
            //.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
            .anyRequest().permitAll();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
