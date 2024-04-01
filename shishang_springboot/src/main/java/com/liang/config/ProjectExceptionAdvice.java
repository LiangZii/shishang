package com.liang.config;

import com.liang.exception.BusinessException;
import com.liang.exception.SystemException;
import com.liang.lang.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProjectExceptionAdvice {

    //    系统异常
    @ExceptionHandler(SystemException.class)
    public Result doSystemException(SystemException ex) {
//        1.记录日志
//        2.上报运维人员
//        3.上报开发人员

//        4.反馈给用户
        return Result.fail(ex.getCode(), ex.getMessage(), null);
    }

    //    业务异常
    @ExceptionHandler(BusinessException.class)
    public Result doBusinessException(BusinessException ex) {
//        1.反馈给用户
        return Result.fail(ex.getCode(), ex.getMessage(), null);
    }

/*
    //    其他异常
    @ExceptionHandler(Exception.class)
    public Result doException() {
//        1.记录日志
//        2.上报运维人员
//        3.上报开发人员

//        4.反馈给用户
        return Result.fail(State.EXCEPTION_ERR, "未知异常，请重试", null);
    }

 */



}
