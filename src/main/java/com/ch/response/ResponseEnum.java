package com.ch.response;

public enum ResponseEnum {
	
	SUCCESS("0000", "成功!"),
	UNKNOWN("9999", "未知异常!"),
	URL_ILLEGAL("404","url拼接异常"),
	PARAM_NOT_EXIST("601","属性不存在");

	private String  code;
	private String message;
	
	ResponseEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
