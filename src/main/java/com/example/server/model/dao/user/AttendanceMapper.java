package com.example.server.model.dao.user;

import com.example.server.model.dto.user.Attendance;
import com.example.server.model.dto.user.MonthJoin;

import com.example.server.model.dto.user.Reaarange;
import com.example.server.model.dto.user.Vacation;
import com.example.server.service.AttendanceService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Mapper
public interface AttendanceMapper {
    Attendance viewAttendance(Long id, Long empId);
    List<MonthJoin> getAllAttendance(Long username);
    int rearrangeAttendance(Long id, String rStartTime, String rEndTime, String contents);
}
