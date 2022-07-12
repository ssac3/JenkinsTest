package com.example.server.service;

import com.example.server.model.dao.scheduler.SchedulerMapper;
import com.example.server.model.dto.user.Attendance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulerService {
    private final SchedulerMapper schedulerMapper;
    LocalDate nowDate = LocalDate.now().minusDays(1) ;
//
    @Scheduled(cron = "0 10 00 * * MON-FRI")
    public void vacationScheduler() {
        String date = nowDate.toString();
        System.out.println(date);
        //근태 정보가 없는 username 중 휴가가 없는 username
        List<Long> emptyUser = schedulerMapper.getEmptyAtndEmpl(date);
        //해당 일자의 휴가 리스트 가져오기 -> 리스트에 있는사람들 휴가 리스트 뽑아올 수 있을까?
        List<Long> yVacation = schedulerMapper.getCronVac(date);

        for (Long item : emptyUser) {
            System.out.println(item.toString());
        }
        emptyUser.removeAll(yVacation);
        Map<String, Object>data = new HashMap<>();
        String aStatus;
        int result = 0;
        if(emptyUser.size() > 0) {
            aStatus = "2";
            data.put("lists", emptyUser);
            data.put("aStatus", aStatus);
            data.put("aDate", date);
            result = schedulerMapper.addCronAttendance(data);
            System.out.println(result);
            if(yVacation.size() > 0) {
                aStatus = "0";
                data.put("lists", yVacation);
                data.put("aStatus", aStatus);
                data.put("aDate", date);
                result = schedulerMapper.addCronAttendance(data);
                System.out.println(result);
            }
        }
        List<Attendance> vacAtdn = schedulerMapper.getCronAtdnId(date);
        for (Attendance item:
              vacAtdn) {
            System.out.println(item.toString());
        }
        result = schedulerMapper.updateCronVacaionAId(vacAtdn);
        System.out.println(result);
    }
}
