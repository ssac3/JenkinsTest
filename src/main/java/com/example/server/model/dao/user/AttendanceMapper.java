package com.example.server.model.dao.user;

import com.example.server.model.dto.user.MonthJoin;

import com.example.server.model.dto.user.Vacation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Mapper
public interface AttendanceMapper {


    List<MonthJoin> getAllAttendance(Long username);
}
