package com.example.server.service;

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

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VacationService {
    private final VacationMapper vacationMapper;

    public ResponseEntity<StatusCode> regVacation(String username, Vacation vacation) {
        vacation.setEmpId(Long.parseLong(username));

        return Optional.of(new JsonResponse())
                .map(v -> Objects.isNull(vacationMapper.viewVacation(vacation)))
                .filter(res -> res)
                .map(v -> {
                    vacationMapper.regVacation(vacation);
                    return new JsonResponse().send(HttpStatus.OK, StatusCode.builder().resCode(0).resMsg("휴가 등록 완료").build());
                })
                .orElseGet(() -> new JsonResponse().send(HttpStatus.OK, StatusCode.builder().resCode(2).resMsg("이미 등록된 휴가입니다.").build()));
    }
    @Transactional
    public ResponseEntity<StatusCode> cancelVacation(String username, Vacation vacation){
        vacation.setEmpId(Long.parseLong(username));
        Vacation vDTO =vacationMapper.viewVacation(vacation);

        return Optional.of(new JsonResponse())
                .map(v -> {
                    return vacationMapper.viewVacation(vDTO);
                })
                .filter(res -> !res.getApprovalFlag().equals("3"))
                .map(res -> {
                    vacationMapper.cancelVacation(vDTO);
                    if(res.getApprovalFlag().equals("1")) {
                        int time;
                        if(vDTO.getType().equals("0")) {
                            time = 8;
                        } else {
                            time = 4;
                        }
                        vacationMapper.returnRestTime(username, time);
                        return new JsonResponse().send(HttpStatus.OK, StatusCode.builder().resCode(0).resMsg("승인된 휴가 취소됨").build());
                    }
                    return new JsonResponse().send(HttpStatus.OK, StatusCode.builder().resCode(0).resMsg("휴가 취소됨").build());
                })
                .orElseGet(() -> new JsonResponse().send(HttpStatus.OK, StatusCode.builder().resCode(2).resMsg("이미 취소된 휴가입니다. 실패").build()));
    }

    public ResponseEntity<StatusCode> viewVacation(String username, Vacation vacation) {
        vacation.setEmpId(Long.parseLong(username));

        return Optional.of(new JsonResponse())
                .map( v -> {
                    Vacation result = vacationMapper.viewVacation(vacation);
                    return new JsonResponse().send(HttpStatus.OK, StatusCode.builder().resCode(0).data(result).build());
        }).orElseGet(() -> new JsonResponse().send(HttpStatus.BAD_REQUEST, StatusCode.builder().resCode(2).resMsg("휴가 정보 조회 실패").build()));
    }
}




