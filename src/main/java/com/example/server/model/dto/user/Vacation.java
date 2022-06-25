package com.example.server.model.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Vacation {
    Long id;
    @JsonProperty("aId")
    Long aId;
    @JsonProperty("empId")
    Long empId;
    String date;
    String vacationType;
    String contents;
    @JsonProperty("approvalFlag")
    String approvalFlag;

    @Builder
    public Vacation(Long id, Long aId, Long empId, String date, String contents, String vacationType, String approvalFlag) {
        this.id = id;
        this.aId = aId;
        this.empId = empId;
        this.date = date;
        this.contents = contents;
        this.vacationType = vacationType;
        this.approvalFlag = approvalFlag;
    }
}
