package com.example.server.service;

import com.example.server.model.dao.scheduler.SchedulerMapper;
import com.example.server.model.dto.user.Attendance;
import com.example.server.model.dto.user.User;
import com.example.server.model.dto.user.Vacation;
import com.mchange.v1.util.ListUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulerService {
    private final SchedulerMapper schedulerMapper;
    LocalDate nowDate = LocalDate.now().minusDays(1) ;
//    @Scheduled(cron = "0 2 21 * * MON-FRI")
    @Scheduled(fixedRate = 990000000)
    public void vacationScheduler() {
        String date = nowDate.toString();
        //근태 정보가 없는 username 중 휴가가 없는 username
        List<Long> emptyUser = schedulerMapper.getEmptyAtndEmpl(date);
        //해당 일자의 휴가 리스트 가져오기 -> 리스트에 있는사람들 휴가 리스트 뽑아올 수 있을까?
        List<Long> yVacation = schedulerMapper.getCronVac(date);
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

            aStatus = "0";
            data.put("lists", yVacation);
            data.put("aStatus", aStatus);
            data.put("aDate", date);
            result = schedulerMapper.addCronAttendance(data);
            System.out.println(result);
        }

        List<Attendance> vacAtdn = schedulerMapper.getCronAtdnId(date);

        for (Attendance item:
              vacAtdn) {
            System.out.println(item.toString());
        }
        result = schedulerMapper.updateCronVacaionAId(vacAtdn);
        System.out.println(result);



//        lists.addAll(emptyUser);
//        lists.removeAll(yVacation);


//        Map<String, Object>data2 = new HashMap<>();
//        data2.put("yVacation", yVacation);
//        data2.put("status", "0");
//        data2.put("date", "2022-07-05");

//        String approvalFlag = "2";
//
//
//
//        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//        for (Long vacation : yVacation) {
//            System.out.println(vacation);
//        }
//        System.out.println("=======================");
//        System.out.println(data.get("list"));
//        System.out.println(emptyUser);
//
//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!");
//
//        System.out.println("=======================");
//        System.out.println(data.get("lists"));
//        System.out.println(data);
//
//
////        int result2 = schedulerMapper.addCronAttendance(data2);
//        System.out.println(result);
////        System.out.println(result2);

    }

}
