package com.example.server.model.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

@Getter
@Setter
@ToString
public class Statistics {
    Long username;
    String aDate;
    String aStartTime;
    String aEndTime;
    String workTime;
    String vacationType;
    String approvalFlag;
}
