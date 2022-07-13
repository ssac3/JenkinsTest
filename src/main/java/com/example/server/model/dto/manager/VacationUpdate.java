package com.example.server.model.dto.manager;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Time;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VacationUpdate {
    @JsonProperty("vId")
    private Long vId;
    private Long username;
    @JsonProperty("approvalFlag")
    private String approvalFlag;
    private String vType;
    private Long restTime;
    private Time startTime;
    private Time endTime;
}
