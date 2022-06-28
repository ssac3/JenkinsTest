package com.example.server.model.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Attendance {
    Long id;
    Long username;
    @JsonProperty("startTime")
    String startTime;
    @JsonProperty("endTime")
    String endTime;
    String status;

    @Builder
    public Attendance(Long id, Long username, String startTime, String endTime, String status) {
        this.id = id;
        this.username = username;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }
}
