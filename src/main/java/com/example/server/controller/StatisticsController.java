package com.example.server.controller;

import com.example.server.config.auth.PrincipalDetails;
import com.example.server.constants.StatusCode;
import com.example.server.model.dto.user.Attendance;
import com.example.server.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @PostMapping("/getMonthStatistics")
    public ResponseEntity<StatusCode> getMonthStatistics(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                         @RequestBody Attendance atdn) {
        System.out.println("principalDetails = " + principalDetails);
        //총 근무시간, 휴가 사용시간, 남은 휴가시간, 월별 근태 현황, 상세 근무 시간
        return statisticsService.getMonthStatistics(principalDetails.getUsername(), atdn);
    }
}
