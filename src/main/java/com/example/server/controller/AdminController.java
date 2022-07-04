package com.example.server.controller;

import com.example.server.config.auth.PrincipalDetails;
import com.example.server.constants.StatusCode;
import com.example.server.model.dto.user.User;
import com.example.server.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    //사원상세보기
    @GetMapping("/admin/empDetail")
    public ResponseEntity<StatusCode> viewEmpDetail(@AuthenticationPrincipal PrincipalDetails principalDetails, User user){
        return adminService.viewEmpDetail(principalDetails.getUsername(), user);
    }

    //사원등록
    @PostMapping("/admin/insertEmp")
    public ResponseEntity<StatusCode> insertEmp(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody User user){
        return adminService.insertEmp(principalDetails.getUsername(), user);
    }

    //사원삭제
    @PostMapping("/admin/deleteEmp")
    public ResponseEntity<StatusCode> deleteEmp (@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody User user) {
        return adminService.deleteEmp(principalDetails.getUsername(), user);
    }

    //사원수정
    @PostMapping("/admin/updateEmp")
    public ResponseEntity<StatusCode> updateEmp (@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody User user){
        System.out.println("사원수정테스트1");
        return adminService.updateEmp(principalDetails.getUsername(), user);
    }

}
