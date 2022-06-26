package com.example.server.model.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MonthJoin {
    Long empId;
    Long aId;
    String aStatus;
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
