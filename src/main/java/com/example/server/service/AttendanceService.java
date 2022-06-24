package com.example.server.service;

import com.example.server.config.auth.PrincipalDetails;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceMapper attendanceMapper;

    public ResponseEntity<StatusCode> getAllAttendance(String username) {
        return Optional.of(new JsonResponse())
                .map(v -> {
                    List<MonthJoin> monthJoin = attendanceMapper.getAllAttendance(Long.parseLong(username));
                    LinkedHashMap<String, Object> map = new LinkedHashMap<>();

                    Object data = monthJoin.stream().map(value -> {
                        map.put("enum", value.getENum());
                        map.put("a_id", value.getAId());
                        map.put("a_s", );
                        map.put(, );
                        map.put(, );
                        map.put(, );
                        map.put(, );
                        map.put(, );
                        map.put(, );
                        map.put(, );
                        map.put(, );
                        map.put(, );
                        map.put(, );
                        map.put(, );
                        return map;
                    });
                    return new JsonResponse().send(HttpStatus.OK, StatusCode.builder().resCode(0).resMsg("근태 정보 조회 완료").data(attendanceMapper.getAllAttendance(Long.parseLong(username))).build());
                }).orElseGet(() -> new JsonResponse().send(HttpStatus.BAD_REQUEST, StatusCode.builder().resCode(0).resMsg("근태 정보 조회 완료").build()));
    }
}
