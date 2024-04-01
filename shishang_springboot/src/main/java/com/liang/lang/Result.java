package com.liang.lang;

import com.liang.config.State;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private int code;//200正常 非200异常
    private String msg;
    private Object data;
    public static Result success(Object data)
    {
        return success(State.OK,"操作成功",data);
    }
    public static Result success(int code,String msg,Object data)
    {
        Result result=new Result();
        result.code=code;
        result.data=data;
        result.msg=msg;
        return result;
    }
    public static Result fail(String msg)
    {
        return fail(State.ERROR,msg,null);
    }
    public static Result fail(int code,String msg)
    {
        return fail(code,msg);
    }
    public static Result fail(int code,String msg,Object data)
    {
        Result result=new Result();
        result.code=code;
        result.data=data;
        result.msg=msg;
        return result;
    }
}
