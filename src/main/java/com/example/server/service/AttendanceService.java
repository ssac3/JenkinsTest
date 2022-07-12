package com.example.server.service;

import com.example.server.constants.JsonResponse;
import com.example.server.constants.StatusCode;
import com.example.server.model.dao.user.AttendanceMapper;
import com.example.server.model.dto.user.MonthJoin;
import com.example.server.model.dto.user.Rearrange;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceMapper attendanceMapper;

    public ResponseEntity<StatusCode> getAllAttendance(String username, Map<String, String> reqMap) {
        String month = reqMap.get("month");
        System.out.println("Service.month:"+month);
        return Optional.of(new JsonResponse())
                .map(v -> {
                    List<MonthJoin> monthJoin = attendanceMapper.getAllAttendance(Long.parseLong(username), month);
                    LinkedHashMap<String, Object> map = new LinkedHashMap<>();
                    Object data = monthJoin.stream().map(value -> {
                        map.put("aId", value.getAId());
                        map.put("aStatus", value.getAStatus() );
                        map.put("aStartTime", value.getAStartTime() );
                        map.put("aDate", value.getADate());
                        map.put("aEndTime", value.getAEndTime() );
                        map.put("rId", value.getRId());
                        map.put("rStartTime", value.getRStartTime());
                        map.put("rEndTime", value.getREndTime());
                        map.put("rContents", value.getRContents());
                        map.put("rApprovalFlag", value.getRApprovalFlag());
                        map.put("vId", value.getVId());
                        map.put("vDate", value.getVDate());
                        map.put("vType", value.getVType());
                        map.put("vApprovalFlag", value.getVApprovalFlag());
                        map.put("vContents", value.getVContents());
                        return map;
                    });
                    return new JsonResponse()
                            .send(HttpStatus.OK, StatusCode
                                    .builder().resCode(0).resMsg("근태 정보 조회 완료").data(data)
                                    .build());
                }).orElseGet(() -> new JsonResponse()
                                    .send(HttpStatus.BAD_REQUEST, StatusCode
                                            .builder().resCode(0).resMsg("근태 정보 조회 실패")
                                            .build()));
    }

    public ResponseEntity<StatusCode> rearrangeAttendance(String username, Rearrange rearrange) {
        System.out.println(rearrange.toString());
        return Optional.of(new JsonResponse())
                .map(v -> Objects.isNull(attendanceMapper.viewAttendance(rearrange.getAId(), Long.parseLong(username))))
                .filter(res -> !res) //null 여부 확인
                . map(v -> Objects.isNull(attendanceMapper.viewRearrange(rearrange)))
                .filter(res -> res)
                .map(v -> {
                    int result = attendanceMapper.rearrangeAttendance(rearrange);
                    if(result > 0) { return new JsonResponse().send(HttpStatus.OK, StatusCode.builder().resCode(0).resMsg("이상 근태 조정요청이 완료되었습니다.").build());}
                    else { return new JsonResponse().send(HttpStatus.OK, StatusCode.builder().resCode(2).resMsg("이상 근태 조정 요청 실패").build()); }
                })
                .orElseGet(() -> new JsonResponse().send(HttpStatus.OK, StatusCode.builder().resCode(3).resMsg("조정 가능한 근태 정보가 업습니다.").build()));

    }
}
