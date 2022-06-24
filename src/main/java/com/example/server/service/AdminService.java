package com.example.server.service;

import com.example.server.constants.JsonResponse;
import com.example.server.constants.StatusCode;
import com.example.server.model.dao.admin.AdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
// 하는 이유 질문
@RequiredArgsConstructor
public class AdminService {

    private final AdminMapper adminMapper;
    private StatusCode statusCode;

    public ResponseEntity<StatusCode> viewEmpList(HttpServletRequest request) {
        String username = request.getAttribute("username").toString();
        System.out.println("username은??~~" + username);
        statusCode = StatusCode.builder().resCode(0).resMsg("사원조회 성공").build();
        statusCode.toString();
        System.out.println("statusCode는??~~"+statusCode);
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

    public ResponseEntity<StatusCode> saveEmp(HttpServletRequest request){
        adminMapper.
    }
}
