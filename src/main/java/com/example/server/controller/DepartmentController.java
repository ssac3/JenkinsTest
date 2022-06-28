package com.example.server.controller;

import com.example.server.config.auth.PrincipalDetails;
import com.example.server.constants.StatusCode;
import com.example.server.model.dto.manager.Department;
import com.example.server.model.dto.manager.RearrangeUpdate;
import com.example.server.model.dto.manager.VacationUpdate;
import com.example.server.model.dto.manager.VacationView;
import com.example.server.service.DepartmentService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("/deptView")
    public ResponseEntity<StatusCode> findByOne(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody Department department) {
        return departmentService.findByOne(principalDetails.getUsername(), department.getId());
    }

    @PostMapping("/deptUpdate")
    public ResponseEntity<StatusCode> updateByOne(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody Department department) {
        return departmentService.updateByOne(principalDetails.getUsername(), department.getId(), department.getStartTime(), department.getEndTime());
    }

    @PostMapping("/vacView")
    public ResponseEntity<StatusCode> findByVacationAll(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return departmentService.findByVacationAll(principalDetails.getUsername());
    }

    @PostMapping("/vacUpdate")
    public ResponseEntity<StatusCode> updateVacationByOne(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody VacationUpdate vacationUpdate){
        return departmentService.updateVacationByOne(principalDetails.getUsername(), vacationUpdate.getVId(), vacationUpdate.getApprovalFlag());
    }

    @PostMapping("/reArrange")
    public ResponseEntity<StatusCode> findByRearrangeAll(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return departmentService.findByRearrangeAll(principalDetails.getUsername());
    }

    @PostMapping("/rarUpdate")
    public ResponseEntity<StatusCode> updateRearrangeByOne(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody RearrangeUpdate rearrangeUpdate){
        return departmentService.updateRearrangeByOne(principalDetails.getUsername(), rearrangeUpdate.getRId(), rearrangeUpdate.getAId(), rearrangeUpdate.getStartTime(), rearrangeUpdate.getEndTime(), rearrangeUpdate.getApprovalFlag());
    }
}
