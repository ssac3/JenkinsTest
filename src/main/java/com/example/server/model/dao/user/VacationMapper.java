package com.example.server.model.dao.user;

import com.example.server.model.dto.manager.Department;
import com.example.server.model.dto.user.Vacation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Map;
import java.util.Optional;

@Repository
@Mapper
public interface VacationMapper {
    int regVacation(Vacation vacation);
    int cancelVacation(Vacation vacation);
    Vacation viewVacation(Vacation vacation);
    int returnRestTime(Long username, Long time);
    Map<String, Time> getAtdnT(Long username);
}
