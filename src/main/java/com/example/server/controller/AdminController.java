package com.example.server.controller;

import com.example.server.constants.StatusCode;
import com.example.server.model.dto.user.User;
import com.example.server.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin")
    public void admin(){
        System.out.println("admin접근");
    }

    // 사원리스트보여주기
    @GetMapping("/admin/viewEmpList")
    public ResponseEntity<StatusCode> viewEmpList(HttpServletRequest request){
        return adminService.viewEmpList(request);
    }

    //사원등록하기기
    /*@PostMapping("/admin/saveEmp")
    public ResponseEntity<StatusCode> saveEmp(@RequestBody User user){
        return "사원저장"
    }*/
}
