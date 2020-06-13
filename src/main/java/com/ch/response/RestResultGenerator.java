package com.ch.response;

import com.github.pagehelper.Page;

import java.util.List;

/**
 * 统一响应体
 */
public class RestResultGenerator {

    /**
     * 带分页正文的成功响应
     */
    public static <T> ResponsePageResult<T> createSuccessPageResult(Page<T> page) {
        ResponsePageResult<T> result = ResponsePageResult.newInstance();
        result.setResultInfo(ResponseEnum.SUCCESS, "");
        result.setCount(page.getTotal());
        result.setData(page.getResult());
        return result;
    }

    public static <T> ResponsePageResult<T> createSuccessPageResult(List<T> data) {
        ResponsePageResult<T> result = ResponsePageResult.newInstance();
        result.setResultInfo(ResponseEnum.SUCCESS, "");
        result.setCount(Long.valueOf(data.size()));
        result.setData(data);
        return result;
    }

    /**
     * 带正文的成功响应
     */
    public static <T> ResponseResult<T> createSuccessResult(T data) {
        ResponseResult<T> result = ResponseResult.newInstance();
        result.setResultInfo(ResponseEnum.SUCCESS, "");
        result.setSuccess(true);
        result.setData(data);
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

    /**
     * 前端xm-selects成功响应
     */
    public static <T> ResponseSelectResult<T> createSuccessSelectResult(T data) {
        ResponseSelectResult<T> result = ResponseSelectResult.newInstance();
        result.setResultInfo(ResponseEnum.SUCCESS, "success");
        result.setData(data);
        return result;
    }
}
