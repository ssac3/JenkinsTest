package com.example.server.controller;

import com.example.server.constants.StatusCode;
import com.example.server.model.dto.manager.Department;
import com.example.server.model.dto.manager.VacationView;
import com.example.server.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping("/deptUpdate")
    public ResponseEntity<StatusCode> findByOne(HttpServletRequest request, @RequestBody Department department){
        return departmentService.findByOne(request, department.getId());
    }

    @PostMapping("/update")
    public ResponseEntity<StatusCode> updateByOne(@RequestBody Department department){
        return departmentService.updateByOne(department.getId(), department.getStartTime(), department.getEndTime());
    }

    @PostMapping("/vacView")
    public ResponseEntity<StatusCode> findByVacationAll(HttpServletRequest request){
        return departmentService.findByVacationAll(request);
    }
}
