package com.example.server.model.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Reaarange {
    Long id;
    @JsonProperty("aId")
    Long aId;
    @JsonProperty("startTime")
    String startTime;
    @JsonProperty("endTime")
    String endTime;
    String contents;
    @JsonProperty("approvalFlag")
    String approvalFlag;


}
