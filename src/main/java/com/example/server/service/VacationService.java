package com.example.server.service;

import com.amazonaws.services.apigateway.model.Op;
import com.example.server.constants.JsonResponse;
import com.example.server.constants.StatusCode;
import com.example.server.model.dao.user.VacationMapper;
import com.example.server.model.dto.user.MonthJoin;
import com.example.server.model.dto.user.Vacation;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.org.apache.bcel.internal.generic.SWITCH;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VacationService {
    private final VacationMapper vacationMapper;

//    public ResponseEntity<StatusCode> regVacation(String username, Vacation vacation) {
//        vacation.setUsername(Long.parseLong(username));
//
//        return Optional.of(new JsonResponse())
//                .map(v -> Objects.isNull(vacationMapper.viewVacation(vacation)))
//                .filter(res -> res)
//                .map(v -> {
//                    vacationMapper.regVacation(vacation);
//                    return new JsonResponse().send(HttpStatus.OK, StatusCode.builder().resCode(0).resMsg("휴가 등록 완료").build());
//                })
//                .orElseGet(() -> new JsonResponse().send(HttpStatus.OK, StatusCode.builder().resCode(2).resMsg("이미 등록된 휴가입니다.").build()));
//    }
    @Transactional
    public ResponseEntity<StatusCode> regVacation(String username, Vacation vacation) {
        vacation.setUsername(Long.parseLong(username));
        Vacation vDTO =vacationMapper.viewVacation(vacation);
        return Optional.of(new JsonResponse())
                .map(v -> Objects.isNull(vacationMapper.viewVacation(vDTO)))
                .filter(viewVac -> viewVac)
                .map(v -> vacationMapper.regVacation(vacation))
                .filter(addVac -> addVac > 0)
                .map(v -> new JsonResponse().send(HttpStatus.OK, StatusCode.builder().resCode(0).resMsg("휴가 등록 완료").build()))
                .orElseGet(() -> new JsonResponse().send(HttpStatus.OK, StatusCode.builder().resCode(2).resMsg("이미 등록된 휴가입니다.").build()));
    }
    @Transactional
    public ResponseEntity<StatusCode> cancelVacation(String username, Vacation vacation){
        vacation.setUsername(Long.parseLong(username));
        Vacation vDTO =vacationMapper.viewVacation(vacation);
        Map<String, Time> data = vacationMapper.getAtdnT(Long.parseLong(username));
        LocalTime startTime = data.get("start_time").toLocalTime();
        LocalTime lunchTime = LocalTime.of(12,00,00);
        LocalTime endTime = data.get("end_time").toLocalTime();

        return Optional.of(new JsonResponse())
                .map(v -> Objects.isNull(vacationMapper.viewVacation(vacation)))
                .filter(viewVac -> !viewVac)    //휴가여부 확인 ? true / false
                .map(res -> {   //휴가 여부 true
                    if (!vDTO.getApprovalFlag().equals("3")) {  //휴가 여부 true -> 휴가 취소 상태 ? true / false
                        if (vDTO.getApprovalFlag().equals("1")) {   //휴가 여부 true -> 휴가 취소 상태 false -> 휴가 승인 여부 ? true / false
                            Long time;
                            if(!vDTO.getVacationType().equals("0")) {   //휴가 여부 true -> 휴가 취소 상태 false -> 휴가 승인 여부 true -> 전일휴가 ? true / false
                                if(vDTO.getVacationType().equals("1")) {    //휴가 여부 true -> 휴가 취소 상태 false -> 휴가 승인 여부 true -> 전일휴가 false ->  오전휴가
                                    time = Duration.between(startTime, lunchTime).getSeconds() / 3600;
                                }
                                else{   //휴가 여부 true -> 휴가 취소 상태 false -> 휴가 승인 여부 true -> 전일휴가 false ->  오후휴가
                                    time = Duration.between(lunchTime.plusHours(1), endTime).getSeconds() /3600;}
                            } else {time = 8L;}
                            int cancelres = vacationMapper.cancelVacation(vDTO);
                            if(cancelres > 0) { //휴가 취소 ? true / false
                                int rest = vacationMapper.returnRestTime(Long.parseLong(username), time);
                                if(rest > 0){ return new JsonResponse().send(HttpStatus.OK, StatusCode.builder().resCode(3).resMsg("승인된 휴가 취소").build());}
                                else { return new JsonResponse().send(HttpStatus.OK, StatusCode.builder().resCode(4).resMsg("승인된 휴가 취소 실패").build());}
                            }
                        } else {
                            int cancelres = vacationMapper.cancelVacation(vDTO);
                            if (cancelres > 0) { return new JsonResponse().send(HttpStatus.OK, StatusCode.builder().resCode(5).resMsg("미승인된 휴가 취소").build());}
                            else { return new JsonResponse().send(HttpStatus.OK, StatusCode.builder().resCode(6).resMsg("미승인된 휴가 취소").build());}
                        }
                    } else {new JsonResponse().send(HttpStatus.OK, StatusCode.builder().resCode(8).resMsg("이미 취소된 휴가입니다. 실패").build());}
                    return new JsonResponse().send(HttpStatus.OK, StatusCode.builder().resCode(9).resMsg("취소할 휴가 조회 실패").build());
                }).orElseGet(() ->new JsonResponse().send(HttpStatus.OK, StatusCode.builder().resCode(10).resMsg("취소할 휴가 조회 실패").build()));

    }

    public ResponseEntity<StatusCode> viewVacation(String username, Vacation vacation) {
        vacation.setUsername(Long.parseLong(username));
        return Optional.of(new JsonResponse())
                .map( v -> {
                    Vacation result = vacationMapper.viewVacation(vacation);
                    return new JsonResponse().send(HttpStatus.OK, StatusCode.builder().resCode(0).data(result).build());
        }).orElseGet(() -> new JsonResponse().send(HttpStatus.BAD_REQUEST, StatusCode.builder().resCode(2).resMsg("휴가 정보 조회 실패").build()));
    }
}




