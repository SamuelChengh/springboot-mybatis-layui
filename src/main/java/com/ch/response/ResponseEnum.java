package com.ch.response;

public enum ResponseEnum {
	
	SUCCESS(0, "成功!"),
	UNKNOWN(9999, "未知异常!");

	private Integer  code;
	private String   message;
	
	ResponseEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
