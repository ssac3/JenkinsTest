package com.example.server.service;

import com.example.server.model.dao.scheduler.SchedulerMapper;
import com.example.server.model.dto.user.Attendance;
import com.example.server.model.dto.user.User;
import com.example.server.model.dto.user.Vacation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
//    @Scheduled(cron = "0 2 21 * * MON-FRI")
    @Scheduled(fixedRate = 10000)
    @Transactional
    public void vacationScheduler() {
        String date = nowDate.toString();
        //근태 정보가 없는 사원들 휴가 리스트 가져오기
        List<User> emptyUser = schedulerMapper.getEmptyAtndEmpl(date);
        //해당 일자의 휴가 리스트 가져오기 -> 리스트에 있는사람들 휴가 리스트 뽑아올 수 있을까?
        List<Vacation> todayVacation = schedulerMapper.getCronVac(date);

        for (User item : emptyUser) {
            System.out.println(item.getUsername());
        }
        System.out.println("=======================");
        for (Vacation vacation : todayVacation) {
            System.out.println(vacation.getDate());
        }
        System.out.println("=======================");

        System.out.println(nowDate);
    }

}
