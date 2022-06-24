package com.example.server.model.dao.user;

import com.example.server.model.dto.user.Vacation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Mapper
public interface VacationMapper {
    int regVacation(Vacation vacation);
    int cancelVacation(Vacation vacation);
    Vacation viewVacation(Vacation vacation);
    int returnRestTime(String username, int time);
}
