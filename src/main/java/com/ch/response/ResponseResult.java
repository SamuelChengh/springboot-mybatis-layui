package com.ch.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseResult<T> implements Serializable {

	private Integer  code;
	private String   message;
	private Boolean  success;
	private T        data;

	public static <T> ResponseResult<T> newInstance() {
        return new ResponseResult<>();
    }
	
	private ResponseResult() {

    }
	
    public void setResultInfo(ResponseEnum responseEnum, String msg) {
        this.code = responseEnum.getCode();
        if(msg.isEmpty()){
			this.message = responseEnum.getMessage();
		}else{
			this.message = msg;
		}
    }
}
