package com.ch.response;

/*
* 统一的响应体
* */
public class RestResultGenerator {

    /**
     * 带正文的成功响应
     */
	public static <T> ResponseResult<T> createSuccessResult(T data) {
        ResponseResult<T> result = ResponseResult.newInstance();
        result.setData(data);
        result.setResultInfo(ResponseEnum.SUCCESS, "");
        result.setSuccess(true);
        return result;
    }

    /**
     * 不带正文的成功响应
     */
	public static <T> ResponseResult<T> createSuccessResult() {
        ResponseResult<T> result = ResponseResult.newInstance();
        result.setResultInfo(ResponseEnum.SUCCESS, "");
        result.setSuccess(true);
        return result;
    }

    /**
     * 失败响应
     */
    public static ResponseResult createErrorResult(ResponseEnum responseEnum) {
        ResponseResult result = ResponseResult.newInstance();
        result.setResultInfo(responseEnum, "");
        result.setSuccess(false);
        return result;
    }

    /**
     * 带信息的失败响应
     */
    public static ResponseResult createErrorResult(ResponseEnum responseEnum, String message) {
        ResponseResult result = ResponseResult.newInstance();
        result.setResultInfo(responseEnum, message);
        result.setSuccess(false);
        return result;
    }
}
