package com.example.server.controller;

import com.example.server.config.auth.PrincipalDetails;
import com.example.server.constants.StatusCode;
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


@RestController
@RequiredArgsConstructor
public class AttendanceManagementController {
    private final AttendanceService attendanceService;

    @PostMapping("/getAllAttendance")   //달력 내 표출될 정보를 불러오기 위한 API
    public ResponseEntity<StatusCode> getAllAttendance(HttpServletRequest request) {
        return attendanceService.getAllAttendance(request);
        // Instance String username 추가
    }

//    @PostMapping("/addVacation")
//    public ResponseEntity<StatusCode> addVacation(@AuthenticationPrincipal PrincipalDetails principalDetails,
//                                                  HttpServletRequest request,
//                                                  @RequestBody Vacation vacation) {
//        User user = principalDetails.getUser();
//        return attendanceService.Vacation(user,vacation);
//    }
}
