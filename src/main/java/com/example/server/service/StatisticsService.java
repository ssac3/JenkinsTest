package com.example.server.service;

import com.example.server.config.auth.PrincipalDetails;
import com.example.server.constants.JsonResponse;
import com.example.server.constants.StatusCode;
import com.example.server.model.dao.user.StatisticsMapper;
import com.example.server.model.dto.user.Attendance;
import com.example.server.model.dto.user.Statistics;
import com.example.server.model.dto.user.Vacation;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.jsse.JSSEUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final StatisticsMapper statisticsMapper;
    private StatusCode statusCode;
    public ResponseEntity<StatusCode> getMonthStatistics(String username, Attendance atdn) {
        atdn.setUsername(Long.parseLong(username));
        List<Attendance> statsList = statisticsMapper.viewMonthAtdn(atdn);
        Map<String, Long> timeMap = statisticsMapper.viewMonthAtdnCnt(atdn);
        Map<String, Long> sumData = statisticsMapper.viewSumTime(atdn);

        Long restTime = Long.parseLong(String.valueOf(timeMap.get("vacation_time")));
        timeMap.remove("vacation_time");
        
        int totalWorkTime = Integer.parseInt(String.valueOf(sumData.get("totalWorkTime"))) / 60 ;
        double totalUseVac = Double.parseDouble(String.valueOf(sumData.get("totalUseVac"))) / 60;
        Long overtime = Long.parseLong(String.valueOf(sumData.get("overtime"))) / 60;
        Object timeMapObject = timeMap;
        Map<String, Object> data = new HashMap<String, Object>();
        System.out.println();
        data.put("startList",statsList);
        data.put("timeMap",timeMapObject);
        data.put("restTime", restTime);
        data.put("totalWorkTIme", totalWorkTime );
        data.put("overtime", overtime);
        data.put("totalUseVac", totalUseVac);
        statusCode = StatusCode.builder().data(data).resCode(0).resMsg("조회 성공").build();

        return new JsonResponse().send(HttpStatus.OK,statusCode);
    }


}
