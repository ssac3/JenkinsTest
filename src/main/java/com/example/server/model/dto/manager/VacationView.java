package com.example.server.model.dto.manager;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VacationView {
    private Long vId;
    private Long username;
    private String date;
    private String type;
    private String contents;
    private String approvalFlag;
}
