package com.example.server.model.dto.manager;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Month {
    Long month;
    @JsonProperty("okCount")
    Long okCount;

    @JsonProperty("lateCount")
    Long lateCount;

    @JsonProperty("absenceCount")
    Long absenceCount;

    @JsonProperty("vacationTime")
    Long vacationTime;
}
