package com.example.server.controller;

import com.example.server.config.auth.PrincipalDetails;
import com.example.server.constants.StatusCode;
import com.example.server.model.dto.user.Attendance;
import com.example.server.model.dto.user.Statistics;
import com.example.server.model.dto.user.Vacation;
import com.example.server.service.StatisticsService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsService statisticsService;

    @PostMapping("/getMonthStatistics")
    public ResponseEntity<StatusCode> getMonthStatistics(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                         @RequestBody Attendance atdn) {
        //총 근무시간, 휴가 사용시간, 남은 휴가시간, 월별 근태 현황, 상세 근무 시간
        return statisticsService.getMonthStatistics(principalDetails.getUsername(), atdn);
    }
}
