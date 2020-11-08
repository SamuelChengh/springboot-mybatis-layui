package com.ch.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponsePageResult<T> implements Serializable {

    private Integer code;
    private String message;
    private Long count;
    private List<T> data;

    public static <T> ResponsePageResult<T> newInstance() {
        return new ResponsePageResult<>();
    }

    private ResponsePageResult() {

    }

    public void setResultInfo(ResponseEnum responseEnum, String msg) {
        this.code = responseEnum.getCode();
        if (msg.isEmpty()) {
            this.message = responseEnum.getMessage();
        } else {
            this.message = msg;
        }
    }
}
