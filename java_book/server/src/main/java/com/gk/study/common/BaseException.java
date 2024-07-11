package com.gk.study.common;

/**
 * 处理业务异常
 */
public class BaseException extends RuntimeException{
    public BaseException(){}

    public BaseException(String msg){
        super(msg);
    }
}
