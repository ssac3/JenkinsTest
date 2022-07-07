package com.example.server.model.dao.scheduler;


import com.example.server.model.dto.user.Attendance;
import com.example.server.model.dto.user.User;
import com.example.server.model.dto.user.Vacation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface SchedulerMapper {
    List<Long> getEmptyAtndEmpl(String date);
    List<Long> getCronVac(String date);
    int addCronAttendance(Map<String, Object> data);
    List<Attendance> getCronAtdnId(String date);
    int updateCronVacaionAId(List<Attendance> vacAtdn);
}
