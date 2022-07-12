package com.example.server.service;

import com.example.server.model.dao.scheduler.SchedulerMapper;
import com.example.server.model.dto.user.Attendance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulerService {
    private final SchedulerMapper schedulerMapper;

    LocalDate nowDate = LocalDate.now().minusDays(1);
    @Scheduled(cron = "0 10 00 * * MON-FRI")
    public void vacationScheduler() {

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
                if(yVacation.size() > 0) {
                    aStatus = "0";
                    data.put("lists", yVacation);
                    data.put("aStatus", aStatus);
                    data.put("aDate", date);
                    result = schedulerMapper.addCronAttendance(data);
                    System.out.println(result);
                }
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
