package com.example.server.model.dto.manager;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RearrangeUpdate {
    @JsonProperty("rId")
    private Long rId;
    @JsonProperty("aId")
    private Long aId;
    private String startTime;
    private String endTime;
    private String approvalFlag;
}
