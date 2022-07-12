package com.example.server.service;

import com.example.server.constants.JsonResponse;
import com.example.server.constants.StatusCode;
import com.example.server.model.dao.commute.CommuteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommuteService {
    private final CommuteMapper commuteMapper;
    private StatusCode statusCode;

    public boolean vacType(Long username,String currentDate ,String type){
        return commuteMapper.checkVacation(username,currentDate).get("vacation_type").equals(type);
    }
    public ResponseEntity<StatusCode> commute(String name){
        Long username = Long.parseLong(name);
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();

        if (commuteMapper.findByUsername(username) > 0){
            LocalTime lunchTimeIn = LocalTime.of(13, 01);
            LocalTime lunchTimeOut = LocalTime.of(12, 00);
            Map<String, Time> dep = commuteMapper.checkDepTime(username);
            LocalTime depStart = dep.get("start_time").toLocalTime();
            LocalTime depEnd = dep.get("end_time").toLocalTime();
            String currentDate = nowDate.toString();
            String currentTime = nowTime.toString();

            boolean vacCheck = commuteMapper.checkVacation(username,currentDate).get("count") == null;
            if (!vacCheck){
                if (vacType(username,currentDate,"1")){
                    if (nowTime.compareTo(depEnd) < 0) {
                        if(commuteMapper.checkInOut(username) > 0){
                            statusCode = StatusCode.builder().resCode(4).resMsg("출근 처리된 사원입니다.").build();
                        }
                        else{
                            commuteMapper.clockIn(username, Time.valueOf(nowTime));
                            statusCode = StatusCode.builder().resCode(0).resMsg("(" + currentTime + ")출근입니다.").build();
                        }
                    }
                    else {
                        if(commuteMapper.checkInOut(username) > 0){
                            if(commuteMapper.checkOut(username) == null) {
                                commuteMapper.clockOut(username, Time.valueOf(nowTime));
                                statusCode = StatusCode.builder().resCode(1).resMsg("(" + currentTime + ")퇴근입니다.").build();
                                if(commuteMapper.checkAtdTime(username)
                                        .get("start_time").toLocalTime().compareTo(lunchTimeIn) <= 0) {
                                    commuteMapper.determine("0",username);
                                }
                                else {
                                    commuteMapper.determine("1", username);
                                }
                            }
                            else{
                                statusCode = StatusCode.builder().resCode(5).resMsg("퇴근 처리된 사원입니다.").build();
                            }

                        }
                        else{
                            commuteMapper.clockOutInsert(username, Time.valueOf(nowTime));
                            statusCode = StatusCode.builder().resCode(1).resMsg("(" + currentTime + ")퇴근입니다.").build();
                        }
                    }
                }
                else if (vacType(username,currentDate,"2")){
                    if (nowTime.compareTo(lunchTimeOut) < 0){
                        if (commuteMapper.checkInOut(username) > 0) {
                            statusCode = StatusCode.builder().resCode(4).resMsg("출근 처리된 사원입니다.").build();
                        }
                        else {
                            commuteMapper.clockIn(username, Time.valueOf(nowTime));
                            statusCode = StatusCode.builder().resCode(0).resMsg("(" + currentTime + ")출근입니다.").build();
                        }
                    }
                    else{
                        if (commuteMapper.checkInOut(username) > 0) {
                            if (commuteMapper.checkOut(username) == null) {
                                commuteMapper.clockOut(username, Time.valueOf(nowTime));
                                statusCode = StatusCode.builder().resCode(1).resMsg("(" + currentTime + ")퇴근입니다.").build();
                                if(commuteMapper.checkAtdTime(username)
                                        .get("start_time").toLocalTime().compareTo(depStart.plusMinutes(1)) <= 0) {
                                    commuteMapper.determine("0",username);
                                }
                                else {
                                    commuteMapper.determine("1", username);
                                }
                            }
                            else{
                                statusCode = StatusCode.builder().resCode(5).resMsg("퇴근 처리된 사원입니다.").build();
                            }
                        }
                        else {
                            commuteMapper.clockOutInsert(username, Time.valueOf(nowTime));
                            statusCode = StatusCode.builder().resCode(1).resMsg("(" + currentTime + ")퇴근입니다.").build();
                        }
                    }
                }
                else {
                    statusCode = StatusCode.builder().resCode(2).resMsg("전일 휴가입니다.").build();
                }
            }
            else{
                if(nowTime.compareTo(depEnd) < 0){
                    if (commuteMapper.checkInOut(username) > 0) {
                        statusCode = StatusCode.builder().resCode(4).resMsg("출근 처리된 사원입니다.").build();
                    }
                    else {
                        commuteMapper.clockIn(username, Time.valueOf(nowTime));
                        statusCode = StatusCode.builder().resCode(0).resMsg("(" + currentTime + ")출근입니다.").build();
                    }
                }
                else{
                    if (commuteMapper.checkInOut(username) > 0) {
                        if(commuteMapper.checkOut(username) == null){
                            commuteMapper.clockOut(username, Time.valueOf(nowTime));
                            statusCode = StatusCode.builder().resCode(1).resMsg("(" + currentTime + ")퇴근입니다.").build();
                            if(commuteMapper.checkAtdTime(username)
                                    .get("start_time").toLocalTime().compareTo(depStart.plusMinutes(1)) <= 0) {
                                commuteMapper.determine("0",username);
                            }
                            else {
                                commuteMapper.determine("1", username);
                            }
                        }
                        else{
                            statusCode = StatusCode.builder().resCode(5).resMsg("퇴근 처리된 사원입니다.").build();
                        }
                    }
                    else{
                        commuteMapper.clockOutInsert(username, Time.valueOf(nowTime));
                        statusCode = StatusCode.builder().resCode(1).resMsg("(" + currentTime + ")퇴근입니다.").build();
                    }
                }
            }
        }
        else {
            statusCode = StatusCode.builder().resCode(3).resMsg("등록된 사원이 아닙니다.").build();
        }
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }
}
