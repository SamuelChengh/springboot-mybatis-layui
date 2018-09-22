package com.ch.response;

public enum ResponseEnum {
	
	SUCCESS(0, "成功!"),
	UNKNOWN(9999, "未知异常!"),
    USER_WRONG_AUT(1000, "无效的用户名或密码!"),
	USER_NOT_EXIST(1001, "用户不存在!"),
	ACCOUNT_EXIST(1002, "账号已存在!");

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
