package com.example.server.model.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Attendance {
    Long id;
    @JsonProperty("empId")
    Long empId;
    @JsonProperty("startTime")
    Date startTime;
    @JsonProperty("endTime")
    Date endTime;
    String status;


}
