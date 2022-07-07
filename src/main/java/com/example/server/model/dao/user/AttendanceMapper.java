package com.example.server.model.dao.user;

import com.example.server.model.dto.user.Attendance;
import com.example.server.model.dto.user.MonthJoin;

import com.example.server.model.dto.user.Rearrange;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AttendanceMapper {
    Attendance viewAttendance(Long aId, Long username);
    List<MonthJoin> getAllAttendance(Long username, String month);
    int rearrangeAttendance(Rearrange rearrange);
    Rearrange viewRearrange(Rearrange rearrange);
}
