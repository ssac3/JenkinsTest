package com.example.server.service;

import com.example.server.constants.JsonResponse;
import com.example.server.constants.StatusCode;
import com.example.server.model.dao.user.VacationMapper;
import com.example.server.model.dto.user.Vacation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VacationService {
    private final VacationMapper vacationMapper;
    StatusCode statusCode;
    public ResponseEntity<StatusCode> addVacation(String username, Vacation vacation) {
        vacation.setEmpId(Long.parseLong(username));
        int result = vacationMapper.regVacation(vacation);

        if (result > 0) {
            statusCode = StatusCode.builder().resCode(0).resMsg("휴가가 정상적으로 등록되었습니다.").build();
        }
        else {statusCode = StatusCode.builder().resCode(2).resMsg("이미 등록된 휴가입니다.").build();}

        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

}
