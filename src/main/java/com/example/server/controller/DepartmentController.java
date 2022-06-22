package com.example.server.controller;

import com.example.server.constants.StatusCode;
import com.example.server.model.dto.manager.Department;
import com.example.server.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dept")
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping("/view")
    public ResponseEntity<StatusCode> findByOne(@RequestBody Department department){
        return departmentService.findByOne(department.getId());
    }

    @PostMapping("/update")
    public ResponseEntity<StatusCode> updateByOne(@RequestBody Department department){
        return departmentService.updateByOne(department.getId(), department.getStartTime(), department.getEndTime());
    }
}
