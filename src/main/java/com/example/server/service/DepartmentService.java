package com.example.server.service;

import com.example.server.constants.JsonResponse;
import com.example.server.constants.StatusCode;
import com.example.server.model.dao.manager.DepartmentMapper;
import com.example.server.model.dto.manager.Department;
import com.example.server.model.dto.manager.VacationView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentMapper departmentMapper;
    private StatusCode statusCode;
    @Transactional
    public ResponseEntity<StatusCode> findByOne(HttpServletRequest request, Long id) {
        String username = request.getAttribute("username").toString();
        if(username != null && !username.equals("")){
            int result = departmentMapper.validDeptId(id);

            if(result == 0){
                statusCode = StatusCode.builder().resCode(1).resMsg("존재하지 않는 부서 ID 입니다.").build();
            }else{
                Department department = departmentMapper.findByDeptInfo(id);
                statusCode = StatusCode.builder().resCode(0).resMsg("부서 정보 조회 성공").data(department).build();
            }
        }
        else{
            System.out.println("[ERR] 유효하지 않는 사용자 정보입니다.");
            statusCode = StatusCode.builder().resCode(2).resMsg("유효하지 않는 사용자 정보입니다.").build();
        }
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

    @Transactional
    public ResponseEntity<StatusCode> updateByOne(Long id, String startTime, String endTime){
        int checkValidId = departmentMapper.validDeptId(id);

        if(checkValidId == 0){
            statusCode = StatusCode.builder().resCode(1).resMsg("존재하지 않는 부서 ID 입니다.").build();
        }else{
            LocalDateTime sDate = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime eDate = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            departmentMapper.updateByOne(sDate, eDate, id);
            statusCode = StatusCode.builder().resCode(0).resMsg("출/퇴근 시간 수정 완료").build();
        }
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }


    public ResponseEntity<StatusCode> findByVacationAll( HttpServletRequest request){
        String userInfo = request.getAttribute("username").toString();
        if(userInfo != null && !userInfo.equals("")){
            List<VacationView> result = departmentMapper.findByVacationAll();
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();

            Object data = result.stream().map(value -> {
                map.put("vId", value.getVId());
                map.put("username", value.getUsername());
                map.put("date", value.getDate());
                map.put("type", value.getType());
                map.put("contents", value.getContents());
                map.put("approvalFlag", value.getApprovalFlag());
                return map;
            });

            statusCode = StatusCode.builder().resCode(0).resMsg("휴가 신청 조회 성공").data(data).build();
        }
        else{
            System.out.println("[ERR] 유효하지 않는 사용자 정보입니다.");
            statusCode = StatusCode.builder().resCode(2).resMsg("유효하지 않는 사용자 정보입니다.").build();
        }

        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

    public ResponseEntity<StatusCode> updateVacationByOne(String userInfo, Long vId, String approvalFlag) {
        System.out.println("userInfo = " + userInfo);

        if (userInfo != null && !userInfo.equals("")) {
            int checkValidId = departmentMapper.validVid(vId);
            System.out.println("checkValidId = " + checkValidId);
            if(checkValidId == 0){
                statusCode = StatusCode.builder().resCode(1).resMsg("존재하지 않는 휴가 ID 입니다.").build();
            }else{
                System.out.println("vId = " + vId);
                System.out.println("approvalFlag = " + approvalFlag);
                departmentMapper.updateVacationByOne(approvalFlag, vId);
                statusCode = StatusCode.builder().resCode(0).resMsg("휴가 수정 완료").build();
            }
        }
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }
}
