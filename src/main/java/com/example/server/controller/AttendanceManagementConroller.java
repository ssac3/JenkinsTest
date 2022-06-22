package com.example.server.controller;

import com.example.server.constants.StatusCode;
import com.example.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequiredArgsConstructor
public class AttendanceManagementConroller {
    private final UserService userService;

    @PostMapping("/getAllAttendance")   //달력 내 표출될 정보를 불러오기 위한 API
    public ResponseEntity<StatusCode> getAllAttendance(HttpServletRequest request) {
        return userService.getAllAttendance(request);
    }
}
