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

import javax.servlet.http.HttpServletRequest;

import static com.amazonaws.services.cloudsearchv2.model.IndexFieldType.Date;

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
//    @PostMapping("mkUsername")
//    public ResponseEntity<StatusCode> mkUsername(@AuthenticationPrincipal PrincipalDetails principalDetails, User user){
//        System.out.println("mkUsername");
//        int year = new Date().get
//        return ;
//    }

    //사원등록
    @PostMapping("/admin/insertEmp")
//    public ResponseEntity<StatusCode> insertEmp(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody User user){
//        return adminService.insertEmp(principalDetails.getUsername(), user); , @RequestPart(value = "data") User user
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
    public ResponseEntity<StatusCode> updateEmp (@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody User user){
        return adminService.updateEmp(principalDetails.getUsername(), user);
    }

}
