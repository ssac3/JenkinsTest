package com.example.server.constants;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
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
