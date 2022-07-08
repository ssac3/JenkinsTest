package com.example.server.model.dto.manager;


import lombok.*;

import java.sql.Time;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EavView {
    private Long okCount;
    private Long failCount;
    private Long rCount;
    private Long lastRCount;
    private Long allVac;
    private Long eveningVac;
    private Long afternoonVac;
    private Long lastAllVac;
    private Long lastEveningVac;
    private Long lastAfternoonVac;
    private Long restTime;
    private Time startTime;
    private Time endTime;
}
