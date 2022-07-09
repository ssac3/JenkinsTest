package com.example.server.controller;

import com.example.server.config.auth.PrincipalDetails;
import com.example.server.constants.StatusCode;
import com.example.server.model.dto.user.User;
import com.example.server.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    // 사원리스트
    @GetMapping("/admin")
    public ResponseEntity<StatusCode> viewEmp(@AuthenticationPrincipal PrincipalDetails principalDetails, User user){
        System.out.println("admin접근");
        return adminService.viewEmp(principalDetails.getUsername(), user);
    }

    // 사원번호 생성
    @GetMapping("/admin/mkUsername")
    public ResponseEntity<StatusCode> mkUsername(){
        System.out.println("mkUsername");
        System.out.println("값은"+adminService.mkUsername());
        return adminService.mkUsername();
    }
    // QR 생성
    @PostMapping("/admin/mkQR")
    public ResponseEntity<StatusCode> mkQR(@RequestBody User user){
        System.out.println("큐알생성");
        System.out.println("이거?"+user.toString());
        try {
            return adminService.mkQR(user);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    //사원등록
    @PostMapping("/admin/insertEmp")
    public ResponseEntity<StatusCode> insertEmp(@RequestPart(value = "image") MultipartFile multipartFile, @RequestPart(value = "data") User user){
        System.out.println("user는"+user);
        System.out.println("쳌쳌" + multipartFile);
        return adminService.insertEmp(multipartFile, "profile", user);
    }

    //사원삭제
    @PostMapping("/admin/deleteEmp")
    public ResponseEntity<StatusCode> deleteEmp (@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody User user) {
        return adminService.deleteEmp(principalDetails.getUsername(), user);
    }
    //사원수정
    @PostMapping("/admin/updateEmp")
    public ResponseEntity<StatusCode> updateEmp (@AuthenticationPrincipal PrincipalDetails principalDetails,  @RequestBody User user){
        System.out.println("user는"+user);
        return adminService.updateEmp(principalDetails.getUsername(), user);
    }

}
