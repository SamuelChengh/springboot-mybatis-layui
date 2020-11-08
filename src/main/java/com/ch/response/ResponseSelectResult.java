package com.ch.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseSelectResult<T> implements Serializable {

    private Integer code;
    private String msg;
    private T data;

    public static <T> ResponseSelectResult<T> newInstance() {
        return new ResponseSelectResult<>();
    }

    private ResponseSelectResult() {

    }

    public void setResultInfo(ResponseEnum responseEnum, String msg) {
        this.code = responseEnum.getCode();
        if (msg.isEmpty()) {
            this.msg = responseEnum.getMessage();
        } else {
            this.msg = msg;
        }
    }

}
