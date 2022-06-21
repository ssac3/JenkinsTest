package com.example.server.service;

import com.example.server.constants.JsonResponse;
import com.example.server.constants.StatusCode;
import com.example.server.model.dao.manager.DepartmentMapper;
import com.example.server.model.dto.manager.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentMapper departmentMapper;
    private StatusCode statusCode;
    @Transactional
    public ResponseEntity<StatusCode> findByOne(Long id) {
        int result = departmentMapper.existDeptId(id);

        if(result == 0){
            statusCode = StatusCode.builder().resCode(1).resMsg("존재하지 않는 부서 ID 입니다.").build();
        }else{
            Department department = departmentMapper.findByDeptInfo(id);
            statusCode = StatusCode.builder().resCode(0).resMsg("부서 정보 조회 성공").data(department).build();
        }
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

    @Transactional
    public ResponseEntity<StatusCode> updateByOne(Long id, String startTime, String endTime){
        int checkValidId = departmentMapper.existDeptId(id);

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
}
