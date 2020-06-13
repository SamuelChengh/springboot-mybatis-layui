package com.ch.response;

public enum ResponseEnum {
	
	SUCCESS(0, "成功"),
	UNKNOWN(999, "未知异常"),
	PROGRAM_EXCEPTION(500, "内部服务器异常"),
	VERIFICATION_CODE_ERROR(101, "验证码不正确"),
	USER_NOT_EXIST(102, "账号不存在"),
    PASSWORD_ERROR(103, "密码不正确"),
	USER_DISABLED(104, "账号已被禁用,请联系管理员"),
	AUTHORITY_NOT(105, "系统访问受限"),
	USER_NAME_EXIST(106, "账号已存在"),
	ROLE_NAME_EXIST(107, "角色名称已存在");

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
