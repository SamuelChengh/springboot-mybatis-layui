package com.ch.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class ResponseResult<T> implements Serializable {

	private String code;
	private String message;
	private Boolean success;
	private T data;

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
