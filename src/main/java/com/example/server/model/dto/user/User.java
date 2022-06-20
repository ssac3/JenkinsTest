package com.example.server.model.dto.user;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor

public class User {
    private Long username;
    private Long dep_id;
    private String password;
    private String name;
    private String email;
    private String img;
    private String gender;
    private String position;
    private String role;
    private Date created_at;
    private String qr_path;
    private Long rest_time;
    private String working_status;
    private String N_password;

    @Builder
    public User(Long username, Long dep_id, String password, String name, String email, String img,
                String gender, String position, String role, Date created_at, String qr_path,
                Long rest_time, String working_status, String N_password) {
        this.username = username;
        this.dep_id = dep_id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.img = img;
        this.gender = gender;
        this.position = position;
        this.role = role;
        this.created_at = created_at;
        this.qr_path = qr_path;
        this.rest_time = rest_time;
        this.working_status = working_status;
        this.N_password = N_password;
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
        return User.builder().username(username).name(name).dep_id(dep_id).img(img).email(email).gender(gender)
                .password(bCryptPasswordEncoder.encode(password)).position(position).role(role)
                .qr_path(qr_path).build();
    }

    public User pwBcrypt(BCryptPasswordEncoder bCryptPasswordEncoder){
        return User.builder().password(bCryptPasswordEncoder.encode(password)).build();
    }

}
