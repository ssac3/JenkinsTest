package com.example.server.service;

import com.example.server.constants.JsonResponse;
import com.example.server.constants.StatusCode;
import com.example.server.model.dao.manager.DepartmentMapper;
import com.example.server.model.dto.manager.Department;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentMapper departmentMapper;

    @Transactional
    public ResponseEntity<StatusCode> findByOne(Long id) {
        StatusCode statusCode;
        int result = departmentMapper.existDeptId(id);

        if(result == 0){
            statusCode = StatusCode.builder().resCode(1).resMsg("존재하지 않는 부서ID입니다.").build();
        }else{
            Department department = departmentMapper.findByDeptInfo(id);
            statusCode = StatusCode.builder().resCode(0).resMsg("부서 정보 조회 성공").data(department).build();
        }
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

    @Transactional
    public ResponseEntity<StatusCode> updateByOne(Long id, String start, String end){
        return null;
    }
}
