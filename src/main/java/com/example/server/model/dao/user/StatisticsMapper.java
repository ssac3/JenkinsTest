package com.example.server.model.dao.user;

import com.example.server.model.dto.user.Attendance;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface StatisticsMapper {
    List<Map> view();
}
