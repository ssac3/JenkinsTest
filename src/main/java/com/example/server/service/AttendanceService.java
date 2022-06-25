package com.example.server.service;

import com.example.server.config.auth.PrincipalDetails;
import com.example.server.constants.JsonResponse;
import com.example.server.constants.StatusCode;
import com.example.server.model.dao.user.AttendanceMapper;
import com.example.server.model.dto.user.Attendance;
import com.example.server.model.dto.user.MonthJoin;
import com.example.server.model.dto.user.Vacation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
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
                        map.put("e_num", value.getENum());
                        map.put("a_id", value.getAId());
                        map.put("a_status", value.getAStatus() );
                        map.put("a_start_time", value.getAStartTime() );
                        map.put("a_end_time", value.getAEndTime() );
                        map.put("r_id", value.getRId());
                        map.put("r_start_time", value.getRStartTime());
                        map.put("r_end_time", value.getREndTime());
                        map.put("r_contents", value.getRContents());
                        map.put("r_approval_flag", value.getRApprovalFlag());
                        map.put("v_id", value.getVId());
                        map.put("v_date", value.getVDate());
                        map.put("v_type", value.getVType());
                        map.put("v_approval_flag", value.getVApprovalFlag());
                        map.put("v_contents", value.getVContents());
                        return map;
                    });
                    return new JsonResponse().send(HttpStatus.OK, StatusCode.builder().resCode(0).resMsg("근태 정보 조회 완료").data(data).build());
                }).orElseGet(() -> new JsonResponse().send(HttpStatus.BAD_REQUEST, StatusCode.builder().resCode(0).resMsg("근태 정보 조회 완료").build()));
    }

    public ResponseEntity<StatusCode> rearrangeAttendance(String username, Attendance attendance) {
        attendance.setEmpId(Long.parseLong(username));

        MonthJoin monthJoin = attendanceMapper.viewAttendance(attendance);

        return Optional.of(new JsonResponse())
                .map(v -> {
                    System.out.println(Optional.of(attendanceMapper.viewAttendance(attendance)));
                    return Optional.of(attendanceMapper.viewAttendance(attendance));})
                .filter(res -> !Objects.isNull(res))
                .map(res -> {
                    int result = attendanceMapper.rearrangeAttendance(monthJoin);
                    if (result > 0){
                        return new JsonResponse().send(HttpStatus.OK, StatusCode.builder().resCode(0).resMsg("이상 근태 조정요청이 완료되었습니다.").build());
                    }
                    else return new JsonResponse().send(HttpStatus.OK, StatusCode.builder().resCode(2).resMsg("이상 근태 조정 요청 실패").build());
                }).orElseGet(() -> new JsonResponse().send(HttpStatus.BAD_REQUEST, StatusCode.builder().resCode(2).resMsg("이상 근태 조회 실패").build()));

    }
}
