package com.example.server.controller;

import com.example.server.constants.StatusCode;
import com.example.server.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping("/dept/view'")
    public ResponseEntity<StatusCode> deptview(HttpServletRequest request, HttpServletResponse response){
        System.out.println(request);
        return null;
    }
}
