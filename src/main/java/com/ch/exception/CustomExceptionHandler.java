package com.ch.exception;

import com.ch.response.ResponseEnum;
import com.ch.response.ResponseResult;
import com.ch.response.RestResultGenerator;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局的自定义异常捕获
 */
@ControllerAdvice
public class CustomExceptionHandler {

    @ResponseBody
    @ExceptionHandler
    public ResponseResult exceptionHandler(Exception e) {

        e.printStackTrace();

        return RestResultGenerator.createErrorResult(ResponseEnum.PROGRAM_EXCEPTION, ResponseEnum.PROGRAM_EXCEPTION.getMessage() + ":" + e.getMessage());
    }
}
