package com.example.server.model.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Vacation {
    Long id;
    @JsonProperty("aId")
    Long aId;
    @JsonProperty("username")
    Long username;
    String date;
    String vacationType;
    String contents;
    @JsonProperty("approvalFlag")
    String approvalFlag;

    @Builder
    public Vacation(Long id, Long aId, Long username, String date, String contents, String vacationType, String approvalFlag) {
        this.id = id;
        this.aId = aId;
        this.username = username;
        this.date = date;
        this.contents = contents;
        this.vacationType = vacationType;
        this.approvalFlag = approvalFlag;
    }
}
