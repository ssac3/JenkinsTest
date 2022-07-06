package com.example.server.controller;

import com.example.server.config.auth.PrincipalDetails;
import com.example.server.constants.StatusCode;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@NoArgsConstructor
public class StatisticsController {
    @PostMapping("/getMonthStatistics")
    public ResponseEntity<StatusCode> getMonthStatistics(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        String username = principalDetails.getUsername();
        //총 근무시간, 휴가 사용시간, 남은 휴가시간, 월별 근태 현황, 상세 근무 시간

        return null;
    }
}
