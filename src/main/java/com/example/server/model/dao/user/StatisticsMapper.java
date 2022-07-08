package com.example.server.model.dao.user;

import com.example.server.model.dto.user.Attendance;
import com.example.server.model.dto.user.Statistics;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface StatisticsMapper {
    List<Attendance> viewMonthAtdn(Attendance atdn);
    Map<String, Long> viewMonthAtdnCnt(Attendance atdn);
    Map<String, Long> viewSumTime(Attendance atdn);

}
