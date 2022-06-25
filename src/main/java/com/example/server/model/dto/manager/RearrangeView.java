package com.example.server.model.dto.manager;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RearrangeView {
    @JsonProperty("rId")
    private Long rId; // rearrange Id
    @JsonProperty("aId")
    private Long aId; // attendenc Id
    private Long username;
    private String name;
    private String img;
    private String contents;
    private String rStartTime;
    private String rEndTime;
    private String startTime;
    private String endTime;
    private String approvalFlag;

}
