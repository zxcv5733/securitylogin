package com.hit.edu.exception;

/**
 * @author: Li dong.
 * @date: 2020/4/30 - 9:49
 */
public class CustomException extends RuntimeException {
    //异常错误编码
    private int code ;
    //异常信息
    private String message;

    private CustomException(){}

    public CustomException(CustomExceptionType exceptionTypeEnum, String message) {
        this.code = exceptionTypeEnum.getCode();
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
