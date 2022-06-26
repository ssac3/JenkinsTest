package com.example.server.controller;

import com.example.server.config.auth.PrincipalDetails;
import com.example.server.constants.StatusCode;
import com.example.server.model.dto.user.Attendance;
import com.example.server.model.dto.user.User;
import com.example.server.model.dto.user.Vacation;
import com.example.server.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;


@RestController
@RequiredArgsConstructor
public class AttendanceManagementController {
    private final AttendanceService attendanceService;

    @PostMapping("/getAllAttendance")   //달력 내 표출될 정보를 불러오기 위한 API
    public ResponseEntity<StatusCode> getAllAttendance(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        String username = principalDetails.getUsername();
        return attendanceService.getAllAttendance(username);
    }

    @PostMapping("/rearrangeAttendance")
    public ResponseEntity<StatusCode> rearrangeAttendance(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                          @RequestBody Map<String, String> reqMap) {
        String username = principalDetails.getUsername();
        return attendanceService.rearrangeAttendance(username, reqMap);
    }


}
