package com.example.server.service;

import com.example.server.constants.JsonResponse;
import com.example.server.constants.StatusCode;
import com.example.server.model.dao.manager.DepartmentMapper;
import com.example.server.model.dto.manager.*;
import com.example.server.model.dto.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentMapper departmentMapper;
    private StatusCode statusCode;

    public ResponseEntity<StatusCode> findByOne(String userInfo, Long id) {
        if (userInfo != null && !userInfo.equals("")) {
            int result = departmentMapper.validDeptId(id);

            if (result == 0) {
                statusCode = StatusCode.builder().resCode(1).resMsg("존재하지 않는 부서 ID 입니다.").build();
            } else {
                Department department = departmentMapper.findByDeptInfo(id);
                statusCode = StatusCode.builder().resCode(0).resMsg("부서 정보 조회 성공").data(department).build();
            }
        } else {
            System.out.println("[ERR] 유효하지 않는 사용자 정보입니다.");
            statusCode = StatusCode.builder().resCode(2).resMsg("유효하지 않는 사용자 정보입니다.").build();
        }
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

    public ResponseEntity<StatusCode> updateByOne(String userInfo, Long id, String startTime, String endTime) {

        if (userInfo != null && !userInfo.equals("")) {
            int checkValidId = departmentMapper.validDeptId(id);

            if (checkValidId == 0) {
                statusCode = StatusCode.builder().resCode(1).resMsg("존재하지 않는 부서 ID 입니다.").build();
            } else {
                LocalDateTime sDate = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                LocalDateTime eDate = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                departmentMapper.updateByOne(sDate, eDate, id);
                statusCode = StatusCode.builder().resCode(0).resMsg("출/퇴근 시간 수정 완료").build();
            }
        } else {
            System.out.println("[ERR] 유효하지 않는 사용자 정보입니다.");
            statusCode = StatusCode.builder().resCode(2).resMsg("유효하지 않는 사용자 정보입니다.").build();
        }
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }


    public ResponseEntity<StatusCode> findByVacationAll(Long id) {
        List<VacationView> result = departmentMapper.findByVacationAll(id);
        System.out.println(result.toArray().toString());
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        Object data = result.stream().map(value -> {
            map.put("vId", value.getVId());
            map.put("username", value.getUsername());
            map.put("name", value.getName());
            map.put("date", value.getDate());
            map.put("type", value.getType());
            map.put("contents", value.getContents());
            map.put("approvalFlag", value.getApprovalFlag());
            map.put("restTime", value.getRestTime());
            return map;
        });

        statusCode = StatusCode.builder().resCode(0).resMsg("휴가 신청 조회 성공").data(data).build();

        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

    public ResponseEntity<StatusCode> updateVacationByOne(String userInfo, Long vId, String approvalFlag) {
        System.out.println("userInfo = " + userInfo);

        if (userInfo != null && !userInfo.equals("")) {
            int checkValidId = departmentMapper.validVid(vId);
            System.out.println("checkValidId = " + checkValidId);
            if (checkValidId == 0) {
                statusCode = StatusCode.builder().resCode(1).resMsg("존재하지 않는 휴가 ID 입니다.").build();
            } else {
                System.out.println("vId = " + vId);
                System.out.println("approvalFlag = " + approvalFlag);
                departmentMapper.updateVacationByOne(approvalFlag, vId);
                statusCode = StatusCode.builder().resCode(0).resMsg("휴가 수정 완료").build();
            }
        }
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

    public ResponseEntity<StatusCode> findByRearrangeAll(Long id) {
        List<RearrangeView> result = departmentMapper.findByRearrangeAll(id);
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        Object data = result.stream().map(value -> {
            map.put("rId", value.getRId());
            map.put("aId", value.getAId());
            map.put("username", value.getUsername());
            map.put("name", value.getName());
            map.put("img", value.getImg());
            map.put("contents", value.getContents());
            map.put("rStartTime", value.getRStartTime());
            map.put("rEndTime", value.getREndTime());
            map.put("startTime", value.getStartTime());
            map.put("endTime", value.getEndTime());
            map.put("approvalFlag", value.getApprovalFlag());
            return map;
        });

        statusCode = StatusCode.builder().resCode(0).resMsg("근태 조정 요청 조회 성공").data(data).build();
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }


    public ResponseEntity<StatusCode> updateRearrangeByOne(String userInfo, Long rId, Long aId, String startTime, String endTime, String approvalFlag) {
        departmentMapper.updateRearrangeByOne(rId, aId, startTime, endTime, approvalFlag);
        ResultAction result = departmentMapper.checkRearrangeUpdate();
        if (result.getResCode() == 0) {
            statusCode = StatusCode.builder().resCode(0).resMsg("성공적으로 근태 조정 요청을 수정했습니다.").build();
        } else if (result.getResCode() == -1) {
            statusCode = StatusCode.builder().resCode(1).resMsg("존재하지 않은 근태 조정 요청 정보입니다.").build();
        } else {
            statusCode = StatusCode.builder().resCode(2).resMsg("알 수 없는 에러 발생").build();
            System.out.println("[ERR]" + result.getResMsg());
        }
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

    public ResponseEntity<StatusCode> findEmpAllByDepId(Long id) {
        List<User> result = departmentMapper.findEmpAllByDepId(id);
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        Object data = result.stream().map(value -> {
            map.put("username", value.getUsername());
            map.put("dName", value.getDepartment());
            map.put("name", value.getName());
            map.put("email", value.getEmail());
            map.put("gender", value.getGender());
            map.put("position", value.getPosition());
            map.put("createdAt", value.getCreatedAt());
            return map;
        });
        statusCode = StatusCode.builder().resCode(0).resMsg("사원별 근태 조회 성공").data(data).build();
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

    public ResponseEntity<StatusCode> findEmplAtndcById(Long username, String findDate) {
        List<EmplAtndcView> result = departmentMapper.findEmplAtndcById(username, findDate);
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        Object data = result.stream().map(value -> {
            map.put("date", value.getDate());
            map.put("startTime", value.getStartTime());
            map.put("endTime", value.getEndTime());
            map.put("status", value.getStatus());
            map.put("vDate", value.getVDate());
            map.put("vType", value.getVType());
            map.put("vContents", value.getVContents());
            map.put("vApprovalFlag", value.getVApprovalFlag());
            return map;
        });

        statusCode = StatusCode.builder().resCode(0).resMsg("사원별 일별 근태 조회 성공").data(data).build();
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

    public ResponseEntity<StatusCode> findEmplAtndStatsById(Long username, Long year) {
        List<Month> result = departmentMapper.findEmplAtndStatsById(username, year);
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        System.out.println(result.toString());
        Object data = result.stream().map(value -> {
            map.put("month", value.getMonth());
            map.put("ok_count", value.getOkCount());
            map.put("late_count", value.getLateCount());
            map.put("absence_count", value.getAbsenceCount());
            map.put("vacation_time", value.getVacationTime());
            return map;
        });

        statusCode = StatusCode.builder().resCode(0).data(data).resMsg("사원별 월별 근태 조회 성공").build();
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

    public ResponseEntity<StatusCode> findEavByUsername(Long username, String findDate){
        System.out.println("username = " + username);
        System.out.println("findDate = " + findDate);
        String lastDate;
        Long eveningWorkTime; // 오전 근무시간
        Long afternoonWorkTime; // 오후 근무시간
        LocalTime launchStart = LocalTime.of(12,00,00);
        LocalTime launchEnd =LocalTime.of(13,00,00);
        Long usedRecent; // 금월 휴가 사용 시간(분 단위)
        Long usedLast; // 전월 휴가 사용 시간(분 단위)
        LinkedHashMap<String, Long> map = new LinkedHashMap<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
            Calendar cal = Calendar.getInstance();
            Date dt = format.parse(findDate);
            cal.setTime(dt);
            cal.add(Calendar.MONTH, -1);
            lastDate = format.format(cal.getTime());

            EavView result = departmentMapper.findEavByUsername(username, findDate, lastDate);
            System.out.println("result = " + result);

            LocalTime start_time = result.getStartTime().toLocalTime();
            LocalTime end_time = result.getEndTime().toLocalTime();
            eveningWorkTime = Duration.between(start_time, launchStart).getSeconds() / 60;
            afternoonWorkTime = Duration.between(launchEnd, end_time).getSeconds() / 60;

            usedRecent = (result.getEveningVac() * eveningWorkTime)
                + (result.getAfternoonVac() * afternoonWorkTime)
                + (eveningWorkTime + afternoonWorkTime) * result.getAllVac();

            usedLast = (result.getLastEveningVac() * eveningWorkTime)
                + (result.getLastAfternoonVac() * afternoonWorkTime)
                + (eveningWorkTime + afternoonWorkTime) * result.getLastAllVac();

            System.out.println("usedRecent = " + usedRecent);
            System.out.println("usedLast = " + usedLast);

            map.put("okCount", result.getOkCount());
            map.put("failCount", result.getFailCount());
            map.put("rCount", result.getRCount());
            map.put("lastRCount", result.getLastRCount());
            map.put("vTime", usedRecent);
            map.put("lastVTime", usedLast);
            map.put("restTime", result.getRestTime());


        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        statusCode = StatusCode.builder().resCode(0).data(map).resMsg("사원별 근태 현황 조회").build();
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }


    public ResponseEntity<StatusCode> findEmplAtndOverTimeByDepId(String depId, String findDate){
        String month = findDate + "-01";
        LocalDate date = LocalDate.parse(month);
        LocalDate preDate = date.minusMonths(1);
        String preFindDate = preDate.toString().substring(0,7);

        List<Map> overTime = departmentMapper.findOverTimeByDepId(Long.parseLong(depId), findDate);
        String preOverTime = departmentMapper.preFindOverTimeByDepId(Long.parseLong(depId), preFindDate);

        Map<String, Object> result = new HashMap<>();
        result.put("overTime", overTime);
        result.put("lastOverTime", preOverTime);

        statusCode = StatusCode.builder().resCode(0).data(result).resMsg("조회 성공").build();
        System.out.println("result = " + statusCode.getData());
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }
}
//        return Optional.of(new JsonResponse())
//                .map(v -> Optional.of()
//                .filter(res -> res != null)
//                .map(v -> {
//                    System.out.println(v);
//                    return new JsonResponse().send(HttpStatus.OK,StatusCode
//                            .builder()
//                            .resCode(0).data(v).resMsg("성공")
//                            .build());
//                })
//                .orElseGet(() -> new JsonResponse()
//                                    .send(HttpStatus.BAD_REQUEST, StatusCode
//                                            .builder()
//                                            .resCode(2)
//                                            .resMsg("통신 실패")
//                                            .build()));
//    }
//}
