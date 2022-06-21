package com.example.server.model.dto.user;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private Long username;
    private Long depId;
    private String password;
    private String name;
    private String email;
    private String img;
    private String gender;
    private String position;
    private String role;
    private Date createdAt;
    private String qrPath;
    private Long restTime;
    private String workingStatus;
    private String nPassword;
    @Builder
    public User(Long username, Long depId, String password, String name, String email,
                String img, String gender, String position, String role, Date createdAt,
                String qrPath, Long restTime, String workingStatus, String nPassword) {
        this.username = username;
        this.depId = depId;
        this.password = password;
        this.name = name;
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
    }

    //        public List<Long> getRole(){
//
//            return new ArrayList<>();
//        }
//    public List<Long> getRoleList(){
//        if(this.role > 0){
//            return Arrays.asList(this.roles.split(","));
//        }
//        return new ArrayList<>();
//    }

    public User toEntity(BCryptPasswordEncoder bCryptPasswordEncoder){
        return User.builder().username(username).name(name).depId(depId).img(img).email(email).gender(gender)
                .password(bCryptPasswordEncoder.encode(password)).position(position).role(role)
                .qrPath(qrPath).build();
    }

    public User pwBcrypt(BCryptPasswordEncoder bCryptPasswordEncoder){
        return User.builder().password(bCryptPasswordEncoder.encode(password)).build();
    }

}
