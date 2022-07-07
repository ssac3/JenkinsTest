package com.example.server.model.dao.commute;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Map;

@Repository
@Mapper
public interface CommuteMapper {
    Long findByUsername(Long username);
    Long checkInOut(Long username);
    void clockIn(Long username, Time currentTime);
    String checkOut(Long username);
    void clockOut(Long username, Time currentTime);
    void clockOutInsert(Long username, Time currentTime);


    void determine(String status,Long username);
    Map<String, Time> checkDepTime(Long username);
    Map<String, Time> checkAtdTime(Long username);

    Map<String, String> checkVacation(Long username, String date);
}
