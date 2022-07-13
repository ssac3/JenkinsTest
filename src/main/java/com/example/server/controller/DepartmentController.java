package com.example.server.controller;

import com.example.server.config.auth.PrincipalDetails;
import com.example.server.constants.StatusCode;
import com.example.server.model.dto.manager.*;
import com.example.server.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

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
    public ResponseEntity<StatusCode> findByVacationAll(@RequestBody Department department) {
        return departmentService.findByVacationAll(department.getId());
    }

    @PostMapping("/vacUpdate")
    public ResponseEntity<StatusCode> updateVacationByOne(@RequestBody VacationUpdate vacationUpdate){
        return departmentService.updateVacationByOne(vacationUpdate.getVId(), vacationUpdate.getApprovalFlag());
    }

    @PostMapping("/rarView")
    public ResponseEntity<StatusCode> findByRearrangeAll(@RequestBody Department department){
        System.out.println("department.getId() = " + department.getId());
        return departmentService.findByRearrangeAll(department.getId());
    }

    @PostMapping("/rarUpdate")
    public ResponseEntity<StatusCode> updateRearrangeByOne(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody RearrangeUpdate rearrangeUpdate){
        return departmentService.updateRearrangeByOne(principalDetails.getUsername(), rearrangeUpdate.getRId(), rearrangeUpdate.getAId(), rearrangeUpdate.getStartTime(), rearrangeUpdate.getEndTime(), rearrangeUpdate.getApprovalFlag());
    }

    @PostMapping("/eivView")
    public ResponseEntity<StatusCode> findEmpAllByDepId(@RequestBody Department department){
        return departmentService.findEmpAllByDepId(department.getId());
    }

    @PostMapping("/eadView")
    public ResponseEntity<StatusCode> findEmplAtndcById(@RequestBody EmplAtndcView emplAtndcView) {
        return departmentService.findEmplAtndcById(emplAtndcView.getUsername(), emplAtndcView.getFindDate());
    }

    @PostMapping("/eamView")
    public ResponseEntity<StatusCode> findEmplAtndStatsById(@RequestBody Map<String, Long> reqMap) {
        return departmentService.findEmplAtndStatsById(reqMap.get("username"), reqMap.get("year"));
    }

    @PostMapping("/eavView")
    public ResponseEntity<StatusCode> findEavByUsername(@RequestBody Map<String, String> reqMap) {
        System.out.println("reqMap = " + reqMap);
        return departmentService.findEavByUsername(Long.parseLong(reqMap.get("username")), reqMap.get("findDate"));
    }

    @PostMapping("/eovView")
    public ResponseEntity<StatusCode> findEovByDepId(@RequestBody Map<String, String> reqMap) {
        return departmentService.findEovByDepId(reqMap.get("depId"), reqMap.get("findDate"));
    }

    @PostMapping("/empView")
    public ResponseEntity<StatusCode> findEpByDepId(@RequestBody Map<String,Long> reqMap) {
        return departmentService.findEpByDepId(reqMap.get("depId"));
    }
}
