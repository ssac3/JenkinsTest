package com.example.server.model.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Attendance {
    Long id;
    Long username;
    String date;
    @JsonProperty("startTime")
    String startTime;
    @JsonProperty("endTime")
    String endTime;
    String status;

    @Builder
    public Attendance(Long id, Long username, String date, String startTime, String endTime, String status) {
        this.id = id;
        this.username = username;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }
}
