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

    @GetMapping("/admin")
    public void admin(@AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("admin접근");
    }

    //사원등록
    @PostMapping("/admin/insertEmp")
    public ResponseEntity<StatusCode> insertEmp(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody User user){
        return adminService.insertEmp(principalDetails.getUsername(), user);
    }

    // 사원리스트보여주기
    @GetMapping("/admin/viewEmpList")
    public ResponseEntity<StatusCode> viewEmpList(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return adminService.viewEmpList(principalDetails.getUsername());
    }

    //사원수정하기
    /*@PutMapping("/admin/updateEmp")
    public ResponseEntity<StatusCode> updateEmp(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody User user){
        adminService.updateEmp()
    }*/


}
