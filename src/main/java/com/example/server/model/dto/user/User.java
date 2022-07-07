package com.example.server.model.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class User {
    private Long depId;
    private String password;
    private String name;
    private Long username;
    private String department;
    private String position;
    private String email;
    private String manager;
    private String location;
    private String qrPath;
    private String img;
    private String gender;
    private String role;
    private Date createdAt;
    private Long restTime;
    private String workingStatus;
    @JsonProperty("nPasswordCheck")
    private String nPasswordCheck;
    @JsonProperty("nPassword")
    private String nPassword;


    @Builder
    public User(Long depId, String password, String name, Long username, String email, String img,
                String gender, String position, String role, Date createdAt, String qrPath, Long restTime,
                String workingStatus, String nPassword, String department, String manager, String location, String nPasswordCheck) {
        this.depId = depId;
        this.password = password;
        this.name = name;
        this.username = username;
        this.email = email;
        this.img = img;
        this.gender = gender;
        this.position = position;
        this.role = role;
        this.createdAt = createdAt;
        this.qrPath = qrPath;
        this.restTime = restTime;
        this.workingStatus = workingStatus;
        this.nPassword = nPassword;
        this.department = department;
        this.manager = manager;
        this.location = location;
        this.nPasswordCheck = nPasswordCheck;
    }

    public User toEntity(BCryptPasswordEncoder bCryptPasswordEncoder){
        return User.builder().username(username).name(name).depId(depId).img(img).email(email).gender(gender)
                .password(bCryptPasswordEncoder.encode(password)).position(position).role(role)
                .qrPath(qrPath).restTime(120L).build();
    }

    public User toInsertEntity(BCryptPasswordEncoder bCryptPasswordEncoder){
        return User.builder().username(username).password(bCryptPasswordEncoder.encode(password)).name(name).depId(depId).img(img).email(email).gender(gender)
                .position(position).role(role).qrPath(qrPath).build();
    }
}
