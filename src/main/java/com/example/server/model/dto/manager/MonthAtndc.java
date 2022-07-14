package com.example.server.model.dto.manager;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MonthAtndc {
    Long username;
    Long aId;
    String aStatus;
    String aDate;
    String aStartTime;
    String aEndTime;
    Long rId;
    String rStartTime;
    String rEndTime;
    String rContents;
    String rApprovalFlag;
    Long vId;
    String vDate;
    String vType;
    String vApprovalFlag;
    String vContents;

}
