package com.example.server.service;

import com.example.server.constants.JsonResponse;
import com.example.server.constants.StatusCode;
import com.example.server.model.dao.user.AttendanceMapper;
import com.example.server.model.dto.user.MonthJoin;
import com.example.server.model.dto.user.Vacation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceMapper attendanceMapper;
    StatusCode statusCode;

    public ResponseEntity<StatusCode> getAllAttendance(HttpServletRequest request) {
        String username = request.getAttribute("username").toString();
        List<MonthJoin> monthJoin = attendanceMapper.getAllAttendance(Long.parseLong(username));
        statusCode = StatusCode.builder().resCode(0).resMsg("근태 정보 조회 완료").data(monthJoin).build();
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }


}
