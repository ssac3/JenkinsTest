package com.example.server.model.dao.scheduler;


import com.example.server.model.dto.user.User;
import com.example.server.model.dto.user.Vacation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SchedulerMapper {
    List<User> getEmptyAtndEmpl(String date);
    List<Vacation> getCronVac(String date);
}
