package com.example.server.model.dto.manager;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmplAtndcView {
    private Long username;
    @JsonProperty("findDate")
    private String findDate;
    private String date;
    private String startTime;
    private String endTime;
    private String status;
    @JsonProperty("vType")
    private String vType;
    @JsonProperty("vContents")
    private String vContents;
    @JsonProperty("vApprovalFlag")
    private String vApprovalFlag;
}
