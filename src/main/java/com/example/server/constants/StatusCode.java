package com.example.server.constants;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Null 값인 필드 제외
public class StatusCode {
    private int resCode;
    private String resMsg;
    private Object data;

    @Builder
    public StatusCode(int resCode, String resMsg, Object data) {
        this.resCode = resCode;
        this.resMsg = resMsg;
        this.data = data;
    }
}
